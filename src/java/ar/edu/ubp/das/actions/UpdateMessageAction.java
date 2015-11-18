package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageEntity;
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
public class UpdateMessageAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("UpdateMessageAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");
        String messageId = this.getForm().getItem("messageId") == null ? "-1" : (String) this.getForm().getItem("messageId");
        String roomType = (String) this.getForm().getItem("roomType");
        String profileType = (String) this.getForm().getItem("profileType");
        String profileId = (String) this.getForm().getItem("profileId");
        
        Client client = ClientBuilder.newClient();
        
        WebTarget messageTarget = client.target("http://localhost:8080/chat/webresources/messages/room/" + roomId + "/id/" + messageId + "/profileId/" + profileId);
        Invocation messageInvocation = messageTarget.request().buildGet();
        Response messageResponse = messageInvocation.invoke();
        
        if(messageResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            List<MessageEntity> messageList = messageResponse.readEntity(new GenericType<List<MessageEntity>>(){});
            
            this.getForm().setItem("messages", messageList);
            this.getForm().setItem("roomType", roomType);
            this.getForm().setItem("profileType", profileType);
            this.gotoPage("/template/user/addMessage.jsp", request, response);
        }
    }
    
}
