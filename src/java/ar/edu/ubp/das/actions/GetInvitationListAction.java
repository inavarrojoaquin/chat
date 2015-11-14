package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.InvitationEntity;
import ar.edu.ubp.das.entities.RoomEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import java.util.LinkedList;
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
public class GetInvitationListAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetInvitationListAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");
        
        Client client = ClientBuilder.newClient();
        WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations/receiver/" + profileId);        
        Invocation invitationInvocation = invitationTarget.request().buildGet();
        Response invitationResponse = invitationInvocation.invoke();
        
        List<DynaActionForm> invitationsList = invitationResponse.readEntity(new GenericType<List<DynaActionForm>>(){});
        
        if(invitationsList != null){
            this.getForm().setItem("invitations", invitationsList);
            this.getForm().setItem("profileId", profileId);
            this.gotoPage("/template/user/invitationList.jsp", request, response);
        }
    }
    
}
