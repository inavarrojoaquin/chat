package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageComplexEntity;
import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        
        List<ProfileEntity> profileList = null;
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-Param: {0}", roomId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-Param: {0}", roomType);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-Param: {0}", profileType);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-Param: {0}", profileId);
        
        Client client = ClientBuilder.newClient();
                
        HttpSession session = request.getSession(false);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-Session-profileList: {0}", session.getAttribute("profileList"));
        
        if(session.getAttribute("profileList") != null){
            profileList = (List<ProfileEntity>) session.getAttribute("profileList");
        }else{
            Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-PRE llamado a PROFILES");
            
            WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles");        
            Invocation profileInvocation = profileTarget.request().buildGet();
            Response profileResponse = profileInvocation.invoke();
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-POS llamado a PROFILES: " + profileResponse.getStatus());
            
            if(profileResponse.getStatus() == 200){
                profileList = profileResponse.readEntity(new GenericType<List<ProfileEntity>>(){});
                session.setAttribute("profileList", profileList);
                Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-Session-profileList: {0}", session.getAttribute("profileList").hashCode());
            }
        }
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-PRE llamado a MESSAGE");
        Form form = new Form();
        form.param("room", roomId);
        form.param("profile", profileId);

        /**Get room's messages*/
        WebTarget messageTarget = client.target("http://localhost:8080/chat/webresources/messages/room/id/profile/id");        
        Invocation messageInvocation = messageTarget.request().buildPost(Entity.form(form));
        Response messageResponse = messageInvocation.invoke();

        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-POS llamado a MESSAGE: {0}", messageResponse.getStatus());

        if(messageResponse.getStatus() == 200 && profileList != null){
            List<MessageEntity> messageList = messageResponse.readEntity(new GenericType<List<MessageEntity>>(){});
            List<MessageComplexEntity> finalMessageList = new LinkedList<>();
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-MENSAJES-LIST {0}", messageList.size());
            
            if(!messageList.isEmpty()){
                for (MessageEntity message : messageList) {
                    for (ProfileEntity profile : profileList) {
                        if(message.getOwner() == profile.getId()){
                            MessageComplexEntity m = new MessageComplexEntity();
                            m.fromMap(message.toMap());
                            m.setOwnerName(profile.getLogin());
                            finalMessageList.add(m);
                        }
                    }
                }
            }
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "GetMessageListAction-FINAL-MENSAJES-LIST {0}", finalMessageList.size());
            
            this.getForm().setItem("messageList", finalMessageList);
            this.getForm().setItem("profileType", profileType);
            this.getForm().setItem("roomType", roomType);
            this.gotoPage("/template/user/messageList.jsp", request, response);
        }
    }
}
