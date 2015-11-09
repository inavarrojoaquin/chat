package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.UserAccessEntity;
import ar.edu.ubp.das.entities.UserLoginEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class GetMessageListAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetMessageListAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");
        String userAccessId = (String) this.getForm().getItem("userAccessId");
        
        Client client = ClientBuilder.newClient();
        
        WebTarget userAccesTarget = client.target("http://localhost:8080/chat/webresources/useraccess/" + userAccessId);        
        Invocation userAccesInvocation = userAccesTarget.request().buildGet();
        Response userAccesResponse = userAccesInvocation.invoke();
        UserAccessEntity userAcces = new UserAccessEntity();
        
        if(userAccesResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            userAcces = userAccesResponse.readEntity(new GenericType<UserAccessEntity>(){});
        }
        
        WebTarget messageTarget = client.target("http://localhost:8080/chat/webresources/messages/room/" + roomId);        
        Invocation messageInvocation = messageTarget.request().buildGet();
        Response messageResponse = messageInvocation.invoke();
        List<MessageEntity> messageList = messageResponse.readEntity(new GenericType<List<MessageEntity>>(){});
        List<MessageEntity> finalMessageList = new LinkedList<>();
        
        if(messageList != null){
            for(MessageEntity m : messageList){
                if(m.getDatetimeOfCreation().after(userAcces.getDatetimeOfAccessStart())){
                    finalMessageList.add(m);
                }
            }
            this.getForm().setItem("messageList", finalMessageList);
            this.gotoPage("/template/user/messageList.jsp", request, response);
        }
    }
    
}
