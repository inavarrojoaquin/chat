package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.InvitationEntity;
import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.RoomEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class NewPrivateRoomAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("NewPrivateRoomAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");
        String title = (String) this.getForm().getItem("title");
        String inviteEmail = (String) this.getForm().getItem("inviteEmail");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "NewPrivateRoomAction-Param: {0}", profileId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "NewPrivateRoomAction-Param: {0}", title);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "NewPrivateRoomAction-Param: {0}", inviteEmail);
        
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setOwner(Integer.parseInt(profileId));
        roomEntity.setName(title);
        roomEntity.setType("private");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "NewPrivateRoomAction-PRE llamado a ROOM");
        
        /**Create private room*/
        Client client = ClientBuilder.newClient();
        WebTarget roomTarget = client.target("http://localhost:8080/chat/webresources/rooms");        
        Invocation roomInvocation = roomTarget.request().buildPost(Entity.json(roomEntity));
        Response roomResponse = roomInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "NewPrivateRoomAction-POS llamado a ROOM: " + roomResponse.getStatus());
        
        if(roomResponse.getStatus() == 200){
            roomEntity = roomResponse.readEntity(new GenericType<RoomEntity>(){});
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "NewPrivateRoomAction-PRE llamado a PROFILE");
            
            /**Get invited profile*/
            Form form = new Form();
            form.param("login", inviteEmail);
            WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles/find/login");        
            Invocation profileInvocation = profileTarget.request().buildPost(Entity.form(form));
            Response profileResponse = profileInvocation.invoke();

            Logger.getLogger(getClass().getName()).log(Level.INFO, "NewPrivateRoomAction-POS llamado a INVITED PROFILE: " + profileResponse.getStatus());
            if(profileResponse.getStatus() == 200){
                ProfileEntity invitedProfile = profileResponse.readEntity(new GenericType<ProfileEntity>(){});
            
                /**Create invitation*/
                InvitationEntity invitationEntity = new InvitationEntity();
                invitationEntity.setRoom(roomEntity.getId());
                invitationEntity.setSender(Integer.parseInt(profileId));
                invitationEntity.setReceiver(invitedProfile.getId());
                invitationEntity.setState("pending");
                
                Logger.getLogger(getClass().getName()).log(Level.INFO, "NewPrivateRoomAction-PRE llamado a INVITATION");

                WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations");        
                Invocation invitationInvocation = invitationTarget.request().buildPost(Entity.json(invitationEntity));
                Response invitationResponse = invitationInvocation.invoke();
                
                Logger.getLogger(getClass().getName()).log(Level.INFO, "NewPrivateRoomAction-POS llamado a INVITATION: " + invitationResponse.getStatus());
                if(invitationResponse.getStatus() == 200){
                    System.out.println("Create private room and send invitation...");
                    response.getWriter().println(roomEntity.getId());
                }
            }
        }
    }
    
}
