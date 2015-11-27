package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.RoomEntity;
import ar.edu.ubp.das.entities.UserAccessEntity;
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
public class RoomAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("RoomAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");
        String roomId = (String) this.getForm().getItem("roomId");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-Param: {0}", profileId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-Param: {0}", roomId);
        
        Client client = ClientBuilder.newClient();
        
        /*Get profile by id*/
        Form profileForm = new Form();
        profileForm.param("id", profileId);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-PRE llamado a Profile");
        
        WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles/find/id");        
        Invocation profileInvocation = profileTarget.request().buildPost(Entity.form(profileForm));
        Response profileResponse = profileInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-POS llamado a Profile: {0}", profileResponse.getStatus());
        
        ProfileEntity profile = profileResponse.readEntity(new GenericType<ProfileEntity>(){});
        
        /**Get policy for roomId and profileId*/
        Form policyForm = new Form();
        policyForm.param("room", roomId);
        policyForm.param("profile", profileId);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-PRE llamado a ROOMACCESSPOLICY");
        
        WebTarget policyTarget = client.target("http://localhost:8080/chat/webresources/roomaccesspolicy/room/id/profile/id");        
        Invocation policyInvocation = policyTarget.request().buildPost(Entity.form(policyForm));
        Response policyResponse = policyInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-POS llamado a ROOMACCESSPOLICY: " + policyResponse.getStatus());
        
        boolean flag = false;
        
        /**Check if the user has been ejected for this room*/
        if(policyResponse.getStatus() == 200){
            flag = true;
        }
        if(!flag){
            UserAccessEntity userAccess = new UserAccessEntity();
            userAccess.setProfile(Integer.parseInt(profileId));
            userAccess.setRoom(Integer.parseInt(roomId));

            Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-PRE llamado a USERACCESS");
            /**Create useraccess*/
            WebTarget userAccessTarget = client.target("http://localhost:8080/chat/webresources/useraccess");        
            Invocation useraccessInvocation = userAccessTarget.request().buildPost(Entity.json(userAccess));
            Response userAccessResponse = useraccessInvocation.invoke();

            Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-POS llamado a USERACCESS: " + userAccessResponse.getStatus());
            
            if(userAccessResponse.getStatus() == 200){
                userAccess = userAccessResponse.readEntity(new GenericType<UserAccessEntity>(){});
            
                Form form = new Form();
                form.param("id", roomId);

                Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-PRE llamado a ROOM");
                /**Get room*/
                WebTarget roomTarget = client.target("http://localhost:8080/chat/webresources/rooms/id");        
                Invocation roomInvocation = roomTarget.request().buildPost(Entity.form(form));
                Response roomResponse = roomInvocation.invoke();

                Logger.getLogger(getClass().getName()).log(Level.INFO, "RoomAction-POS llamado a ROOM: " + roomResponse.getStatus());
                
                if(roomResponse.getStatus() == 200){
                    RoomEntity roomEntity = roomResponse.readEntity(new GenericType<RoomEntity>(){});
                
                    this.getForm().setItem("profile", profile);
                    this.getForm().setItem("room", roomEntity);
                    this.getForm().setItem("userAccess", userAccess);
                }
            }
        }else{
            this.getForm().setItem("accessDenied", "true");
        }
        this.gotoPage("/template/user/room.jsp", request, response);        
    }
    
}
