package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.InvitationEntity;
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
public class UpdateStateInvitationAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("UpdateStateInvitationAction:execute");
        
        String invitationId = (String) this.getForm().getItem("id");
        String newState = (String) this.getForm().getItem("newState");
        
        Client client = ClientBuilder.newClient();
        WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations/" + invitationId);        
        Invocation invitationInvocation = invitationTarget.request().buildGet();
        Response invitationResponse = invitationInvocation.invoke();
        
        InvitationEntity invitationEntity = invitationResponse.readEntity(new GenericType<InvitationEntity>(){});
        
        if(invitationEntity != null){
            invitationEntity.setState(newState);
            
            WebTarget invitationUpdateTarget = client.target("http://localhost:8080/chat/webresources/invitations");        
            Invocation invitationUpdateInvocation = invitationUpdateTarget.request().buildPut(Entity.json(invitationEntity));
            Response invitationUpdateResponse = invitationUpdateInvocation.invoke();
                        
        }
    }
    
}
