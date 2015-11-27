package ar.edu.ubp.das.actions;

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
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class GetInvitationListAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetInvitationListAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");
        String userLoginStart = (String) this.getForm().getItem("userLoginStart");
        
        Client client = ClientBuilder.newClient();
 
        /**Get invitations*/
        Form form = new Form();
        form.param("id", profileId);
        WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations/receiver/id");        
        Invocation invitationInvocation = invitationTarget.request().buildPost(Entity.form(form));
        Response invitationResponse = invitationInvocation.invoke();
        
        List<DynaActionForm> invitationsList = invitationResponse.readEntity(new GenericType<List<DynaActionForm>>(){});
        
        if(invitationsList != null){
            this.getForm().setItem("invitations", invitationsList);
            this.getForm().setItem("profileId", profileId);
            this.getForm().setItem("userLoginStart", userLoginStart);
            this.gotoPage("/template/user/invitationList.jsp", request, response);
        }
    }
    
}
