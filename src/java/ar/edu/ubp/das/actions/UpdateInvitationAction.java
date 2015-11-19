package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;
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
public class UpdateInvitationAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("UpdateInvitationAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");
        String invitationId = (String) this.getForm().getItem("invitationId");
        
        List<DynaActionForm> invitationsList;
        
        Client client = ClientBuilder.newClient();
        /*No invitations in this moment*/
        if(invitationId == null){
            WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations/receiver/" + profileId);        
            Invocation invitationInvocation = invitationTarget.request().buildGet();
            Response invitationResponse = invitationInvocation.invoke();
            
            invitationsList = invitationResponse.readEntity(new GenericType<List<DynaActionForm>>(){});
            
        }else{
            WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations/receiver/" + profileId + "/invitationId/" + invitationId);        
            Invocation invitationInvocation = invitationTarget.request().buildGet();
            Response invitationResponse = invitationInvocation.invoke();
            
            invitationsList = invitationResponse.readEntity(new GenericType<List<DynaActionForm>>(){});
        }
        
        if(invitationsList != null && !invitationsList.isEmpty()){
                this.getForm().setItem("invitations", invitationsList);
                this.getForm().setItem("profileId", profileId);
                this.gotoPage("/template/user/invitationList.jsp", request, response);
        }
        else{
            response.getWriter().println("empty");
        }
    }
    
}
