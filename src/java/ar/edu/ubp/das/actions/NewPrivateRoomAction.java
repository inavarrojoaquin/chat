package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.InvitationEntity;
import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.RoomEntity;
import ar.edu.ubp.das.entities.UserAccessEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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
        
        String profileId = (String) this.getForm().getItem("profile");
        String title = (String) this.getForm().getItem("title");
        String inviteEmail = (String) this.getForm().getItem("inviteEmail");
        
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setOwner(Integer.parseInt(profileId));
        roomEntity.setName(title);
        roomEntity.setType("private");
        
        /**Create private room*/
        Client client = ClientBuilder.newClient();
        WebTarget roomTarget = client.target("http://localhost:8080/chat/webresources/rooms");        
        Invocation roomInvocation = roomTarget.request().buildPost(Entity.json(roomEntity));
        Response roomResponse = roomInvocation.invoke();
        
        roomEntity = roomResponse.readEntity(new GenericType<RoomEntity>(){});
        
        UserAccessEntity userAccess = new UserAccessEntity();
        userAccess.setProfile(Integer.parseInt(profileId));
        userAccess.setRoom(roomEntity.getId());

        /**Create user access for private room*/
        WebTarget userAccessTarget = client.target("http://localhost:8080/chat/webresources/useraccess");        
        Invocation useraccessInvocation = userAccessTarget.request().buildPost(Entity.json(userAccess));
        Response res = useraccessInvocation.invoke();
        
        userAccess = res.readEntity(new GenericType<UserAccessEntity>(){});
        
        /**Get invited profile*/
        WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles/login/" + inviteEmail);        
        Invocation profileInvocation = profileTarget.request().buildGet();
        Response profileResponse = profileInvocation.invoke();
        
        ProfileEntity invitedProfile = profileResponse.readEntity(new GenericType<ProfileEntity>(){});
        
        /**Create invitation*/
        InvitationEntity invitationEntity = new InvitationEntity();
        invitationEntity.setRoom(roomEntity.getId());
        invitationEntity.setSender(Integer.parseInt(profileId));
        invitationEntity.setReceiver(invitedProfile.getId());
        invitationEntity.setState("pending");
        
        WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations");        
        Invocation invitationInvocation = invitationTarget.request().buildPost(Entity.json(invitationEntity));
        Response invitationResponse = invitationInvocation.invoke();
        
        invitationEntity = invitationResponse.readEntity(new GenericType<InvitationEntity>(){});
        
        this.getForm().setItem("roomId", roomEntity.getId());
        this.getForm().setItem("roomName", roomEntity.getName());
        this.getForm().setItem("profileId", profileId);
        this.getForm().setItem("userAccess", userAccess);
        this.gotoPage("/template/user/room.jsp", request, response);
    }
    
}
