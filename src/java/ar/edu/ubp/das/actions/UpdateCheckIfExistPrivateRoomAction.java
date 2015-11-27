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
public class UpdateCheckIfExistPrivateRoomAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("UpdateCheckIfExistPrivateRoomAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateCheckIfExistPrivateRoomAction-Param: {0}", roomId);
        
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("id", roomId);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "PRE llamado a PRIVATEROOM");
        
        /**Get room id*/
        WebTarget privateRoomsTarget = client.target("http://localhost:8080/chat/webresources/rooms/id");        
        Invocation privateRoomsInvocation = privateRoomsTarget.request().buildPost(Entity.form(form));
        Response privateRoomsResponse = privateRoomsInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "POS llamado a PRIVATEROOM: " + privateRoomsResponse.getStatus());
        
        if(privateRoomsResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode()){
            response.getWriter().println("not_found");
        }
        
    }
    
}
