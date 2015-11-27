package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.InvitationEntity;
import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.RoomEntity;
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
public class InviteParticipantAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("InviteParticipantAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");
        String roomId = (String) this.getForm().getItem("roomId");
        String participantList = (String) this.getForm().getItem("participantList");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "InviteParticipantAction-Param: {0}", profileId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "InviteParticipantAction-Param: {0}", roomId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "InviteParticipantAction-Param: {0}", participantList);
        
        Client client = ClientBuilder.newClient();
        
        String[] loginList = participantList.split(";");
            
        for (String login : loginList) {
            System.out.println("login: " + login);
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "InviteParticipantAction-PRE llamado a INVITATION");

            Form form = new Form();
            form.param("room", roomId);
            form.param("sender", profileId);
            form.param("login", login);
            WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations/invite/participant/to/room");        
            Invocation invitationInvocation = invitationTarget.request().buildPost(Entity.form(form));
            Response invitationResponse = invitationInvocation.invoke();

            Logger.getLogger(getClass().getName()).log(Level.INFO, "InviteParticipantAction-POS llamado a INVITATION: " + invitationResponse.getStatus());
            if(invitationResponse.getStatus() == 200){
                System.out.println("Send invitation...");
            }
        }
    }
    
}
