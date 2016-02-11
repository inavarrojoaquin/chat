package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageEntity;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class SendMessageAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("SendMessageAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");
        String profileId = (String) this.getForm().getItem("profileId");
        String message = (String) this.getForm().getItem("message");
        String roomType = (String) this.getForm().getItem("roomType");
        String profileType = (String) this.getForm().getItem("profileType");
        String profileLogin = (String) this.getForm().getItem("profileLogin");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "SendMessageAction-Param: {0}", roomId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "SendMessageAction-Param: {0}", profileId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "SendMessageAction-Param: {0}", message);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "SendMessageAction-Param: {0}", roomType);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "SendMessageAction-Param: {0}", profileType);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "SendMessageAction-Param: {0}", profileLogin);
        
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setRoom(Integer.parseInt(roomId));
        messageEntity.setOwner(Integer.parseInt(profileId));
        messageEntity.setBody(message);
        messageEntity.setState(roomType);

        Client client = ClientBuilder.newClient();
        WebTarget messageTarget = client.target("http://localhost:8080/chat/webresources/messages");        
        Invocation messageInvocation = messageTarget.request().buildPost(Entity.json(messageEntity));
        Response messageResponse = messageInvocation.invoke();
        
        if(messageResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            messageEntity = messageResponse.readEntity(new GenericType<MessageEntity>(){});
            
            this.getForm().setItem("message", messageEntity);
            this.getForm().setItem("roomType", roomType);
            this.getForm().setItem("profileType", profileType);
            this.getForm().setItem("profileLogin", profileLogin);
            this.gotoPage("/template/user/addMessage.jsp", request, response);
        }
    }
    
}
