package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.entities.RoomEntity;
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
public class SendMessageAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("SendMessageAction:execute");
        
        
        String roomId = (String) this.getForm().getItem("roomId");
        String profileId = (String) this.getForm().getItem("profileId");
        String message = (String) this.getForm().getItem("message");
        
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setRoom(Integer.parseInt(roomId));
        messageEntity.setOwner(Integer.parseInt(profileId));
        messageEntity.setBody(message);
        messageEntity.setState("public");

        Client client = ClientBuilder.newClient();
        WebTarget messageTarget = client.target("http://localhost:8080/chat/webresources/messages");        
        Invocation messageInvocation = messageTarget.request().buildPost(Entity.json(messageEntity));
        Response messageResponse = messageInvocation.invoke();
        
        if(messageResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            messageEntity = messageResponse.readEntity(new GenericType<MessageEntity>(){});
            
            this.getForm().setItem("message", messageEntity);
            this.gotoPage("/template/user/addMessage.jsp", request, response);
        }
    }
    
}
