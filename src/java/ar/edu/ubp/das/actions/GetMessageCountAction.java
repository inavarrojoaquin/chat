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
public class GetMessageCountAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetMessageCountAction:execute");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String roomId = (String) this.getForm().getItem("roomId");
        String profileId = (String) this.getForm().getItem("profileId");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageCountAction-Param: {0}", roomId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageCountAction-Param: {0}", profileId);
        
        Client client = ClientBuilder.newClient();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageCountAction-PRE llamado a MESSAGE");
        Form form = new Form();
        form.param("room", roomId);
        form.param("profile", profileId);

        /**Get room's messages*/
        WebTarget messageTarget = client.target("http://localhost:8080/chat/webresources/messages/room/id/profile/id");        
        Invocation messageInvocation = messageTarget.request().buildPost(Entity.form(form));
        Response messageResponse = messageInvocation.invoke();

        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageCountAction-POS llamado a MESSAGE: {0}", messageResponse.getStatus());

        if(messageResponse.getStatus() == 200){
            String json = messageResponse.readEntity(String.class);
            
            response.getWriter().write(json);
        }
    }
}
