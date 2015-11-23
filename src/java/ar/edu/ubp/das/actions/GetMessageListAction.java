package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.entities.UserAccessEntity;
import ar.edu.ubp.das.entities.UserLoginEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
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
        String roomType = (String) this.getForm().getItem("roomType");
        String profileType = (String) this.getForm().getItem("profileType");
        String profileId = (String) this.getForm().getItem("profileId");
        
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("id", profileId);
        /**Get last login profile only for admin*/
        WebTarget userLoginTarget = client.target("http://localhost:8080/chat/webresources/userslogins/lastlogin/profile/id");        
        Invocation userLoginInvocation = userLoginTarget.request().buildPost(Entity.form(form));
        Response userLoginResponse = userLoginInvocation.invoke();
        UserLoginEntity userLogin = new UserLoginEntity();
        
        if(userLoginResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            userLogin = userLoginResponse.readEntity(new GenericType<UserLoginEntity>(){});
                
            Form form1 = new Form();
            form1.param("id", roomId);
            /**Get room's messages*/
            WebTarget messageTarget = client.target("http://localhost:8080/chat/webresources/messages/room/id");        
            Invocation messageInvocation = messageTarget.request().buildPost(Entity.form(form1));
            Response messageResponse = messageInvocation.invoke();

            List<MessageEntity> messageList = messageResponse.readEntity(new GenericType<List<MessageEntity>>(){});
            List<MessageEntity> finalMessageList = new LinkedList<>();

            if(messageList != null){
                if(profileType.equals("ADMIN")){
                    for(MessageEntity m : messageList){
                        if(m.getDatetimeOfCreation().after(userLogin.getDatetimeOfAccessStart())){
                            finalMessageList.add(m);
                        }
                    }                 
                    /**Send final list*/
                    this.getForm().setItem("messageList", finalMessageList);
                }else{
                    /**Send empty list*/
                    this.getForm().setItem("messageList", finalMessageList);
                }

                this.getForm().setItem("profileType", profileType);
                this.getForm().setItem("roomType", roomType);
                this.gotoPage("/template/user/messageList.jsp", request, response);
            }
        }
    }
}
