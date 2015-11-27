package ar.edu.ubp.das.actions;

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
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "DeletePrivateRoomAction-Param: {0}", roomId);
       
        Client client = ClientBuilder.newClient();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "DeletePrivateRoomAction-PRE llamado a ROOM");
        
        /**Delete private room id*/
        Form form = new Form();
        form.param("id", roomId);
        WebTarget roomTarget = client.target("http://localhost:8080/chat/webresources/rooms/delete/room");        
        Invocation roomInvocation = roomTarget.request().buildPost(Entity.form(form));
        Response roomResponse = roomInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "DeletePrivateRoomAction-POS llamado a ROOM: " + roomResponse.getStatus());
        
        if(roomResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            System.out.println("Delete private room: " + roomId);  
        }
    }
    
}
