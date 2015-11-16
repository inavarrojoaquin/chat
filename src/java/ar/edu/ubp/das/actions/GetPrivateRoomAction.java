package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.RoomEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class GetPrivateRoomAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetPrivateRoomAction:execute");
        
        String owner = (String) this.getForm().getItem("profileId");
        
        Client client = ClientBuilder.newClient();
        WebTarget privateRoomsTarget = client.target("http://localhost:8080/chat/webresources/rooms/owner/" + owner);        
        Invocation privateRoomsInvocation = privateRoomsTarget.request().buildGet();
        Response privateRoomsResponse = privateRoomsInvocation.invoke();
        List<RoomEntity> privateRoomsList = privateRoomsResponse.readEntity(new GenericType<List<RoomEntity>>(){});
        
        if(privateRoomsList != null){
            this.getForm().setItem("privateRooms", privateRoomsList);
            this.getForm().setItem("profileId", owner);
            this.gotoPage("/template/user/privateRoomList.jsp", request, response);
        }else {
            response.getWriter().println("Empty private rooms");
        }
    }
    
}
