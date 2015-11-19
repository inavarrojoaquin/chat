package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.entities.RoomAccessPolicyEntity;
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
public class UpdateCheckAccessPolicyAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("UpdateCheckAccessPolicyAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");
        String profileId = (String) this.getForm().getItem("profileId");
        
        Client client = ClientBuilder.newClient();
        
        WebTarget policyTarget = client.target("http://localhost:8080/chat/webresources/roomaccesspolicy/room/" + roomId + "/profileId/" + profileId);
        Invocation policyInvocation = policyTarget.request().buildGet();
        Response policyResponse = policyInvocation.invoke();
        
        if(policyResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            response.getWriter().println("ProfileEjected");
        }
    }
    
}
