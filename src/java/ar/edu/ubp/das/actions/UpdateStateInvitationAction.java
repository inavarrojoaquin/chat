package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.InvitationEntity;
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
import javax.ws.rs.core.Form;
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
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateStateInvitationAction-Param: {0}", invitationId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateStateInvitationAction-Param: {0}", newState);
        
        Client client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("id", invitationId);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "PRE llamado a INVITATIONS");
        
        /**Get invitation id*/
        WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations/find/id");        
        Invocation invitationInvocation = invitationTarget.request().buildPost(Entity.form(form));
        Response invitationResponse = invitationInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "POS llamado a INVITATIONS: " + invitationResponse.getStatus());
         
        InvitationEntity invitationEntity = invitationResponse.readEntity(new GenericType<InvitationEntity>(){});
        
        if(invitationEntity != null){
            invitationEntity.setState(newState);
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "PRE llamado a INVITATIONSUPDATE");
            
            WebTarget invitationUpdateTarget = client.target("http://localhost:8080/chat/webresources/invitations");        
            Invocation invitationUpdateInvocation = invitationUpdateTarget.request().buildPut(Entity.json(invitationEntity));
            Response invitationUpdateResponse = invitationUpdateInvocation.invoke();
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "POS llamado a INVITATIONSUPDATE: " + invitationUpdateResponse.getStatus());
                        
        }
    }
    
}
