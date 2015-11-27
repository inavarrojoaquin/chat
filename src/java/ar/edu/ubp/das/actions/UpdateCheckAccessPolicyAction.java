package ar.edu.ubp.das.actions;

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
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateCheckAccessPolicyAction-Param: {0}", roomId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateCheckAccessPolicyAction-Param: {0}", profileId);
        
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("room", roomId).param("profile", profileId);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateCheckAccessPolicyAction-PRE llamado a POLICY");
        
        WebTarget policyTarget = client.target("http://localhost:8080/chat/webresources/roomaccesspolicy/room/id/profile/id");
        Invocation policyInvocation = policyTarget.request().buildPost(Entity.form(form));
        Response policyResponse = policyInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateCheckAccessPolicyAction-POS llamado a POLICY: " + policyResponse.getStatus());
        
        if(policyResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            response.getWriter().println("ProfileEjected");
        }
    }
    
}
