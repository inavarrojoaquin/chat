package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.mvc.actions.Action;
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
public class DeleteMessageAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("DeleteMessageAction:execute");
        
        String messageId = (String) this.getForm().getItem("id");
       
        Client client = ClientBuilder.newClient();
        WebTarget messageTarget = client.target("http://localhost:8080/chat/webresources/messages/delete/" + messageId);        
        Invocation messageInvocation = messageTarget.request().buildGet();
        Response messageResponse = messageInvocation.invoke();
        
        if(messageResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            System.out.println("Delete message: " + messageId);  
        }
    }
    
}
