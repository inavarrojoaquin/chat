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
public class DeleteMessageAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("DeleteMessageAction:execute");
        
        String messageId = (String) this.getForm().getItem("id");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "DeleteMessageAction-Param: {0}", messageId);
       
        Client client = ClientBuilder.newClient();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "DeleteMessageAction-PRE llamado a MESSAGE");
        
        /**Delete message id*/
        Form form = new Form();
        form.param("id", messageId);
        WebTarget messageTarget = client.target("http://localhost:8080/chat/webresources/messages/delete/message");        
        Invocation messageInvocation = messageTarget.request().buildPost(Entity.form(form));
        Response messageResponse = messageInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "DeleteMessageAction-POS llamado a MESSAGE: " + messageResponse.getStatus());
        
        if(messageResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            System.out.println("Delete message: " + messageId);  
        }
    }
    
}
