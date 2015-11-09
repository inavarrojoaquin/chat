package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.RoomEntity;
import ar.edu.ubp.das.entities.UserAccessEntity;
import ar.edu.ubp.das.mvc.actions.Action;
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
public class RoomAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("RoomAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");
        String roomId = (String) this.getForm().getItem("roomId");
        String roomName = (String) this.getForm().getItem("roomName");
        
        UserAccessEntity userAccess = new UserAccessEntity();
        userAccess.setProfile(Integer.parseInt(profileId));
        userAccess.setRoom(Integer.parseInt(roomId));

        Client client = ClientBuilder.newClient();
        
        WebTarget userAccessTarget = client.target("http://localhost:8080/chat/webresources/useraccess");        
        Invocation useraccessInvocation = userAccessTarget.request().buildPost(Entity.json(userAccess));
        Response res = useraccessInvocation.invoke();
        
        if(res.getStatus() != Response.Status.CONFLICT.getStatusCode()){
            userAccess = res.readEntity(new GenericType<UserAccessEntity>(){});
        }
        
        this.getForm().setItem("profileId", this.getForm().getItem("profileId"));
        this.getForm().setItem("roomId", this.getForm().getItem("roomId"));
        this.getForm().setItem("roomName", this.getForm().getItem("roomName"));
        this.getForm().setItem("userAccess", userAccess);
        
        this.gotoPage("/template/user/room.jsp", request, response);        
    }
    
}
