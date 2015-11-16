package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.mvc.actions.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class DeletePrivateRoomAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("DeletePrivateRoomAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");
       
        Client client = ClientBuilder.newClient();
        WebTarget roomTarget = client.target("http://localhost:8080/chat/webresources/rooms/delete/" + roomId);        
        Invocation roomInvocation = roomTarget.request().buildGet();
        Response roomResponse = roomInvocation.invoke();
        
        if(roomResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            System.out.println("Delete private room: " + roomId);  
        }
    }
    
}
