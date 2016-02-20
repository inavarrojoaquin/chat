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
public class UpdateCheckAccessPolicyEnableAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("UpdateCheckAccessPolicyEnableAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateCheckAccessPolicyEnableAction-Param: {0}", profileId);
        
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("profileId", profileId);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateCheckAccessPolicyAction-PRE llamado a POLICY");
        
        WebTarget policyTarget = client.target("http://localhost:8080/chat/webresources/roomaccesspolicy/enable/profile/id");
        Invocation policyInvocation = policyTarget.request().buildPost(Entity.form(form));
        Response policyResponse = policyInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateCheckAccessPolicyEnableAction-POS llamado a POLICY: " + policyResponse.getStatus());
        
        if(policyResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            String json = policyResponse.readEntity(String.class);
            
            System.out.println("Enable Again JSON: " + json);
        
            response.getWriter().write(json);
        }
    }
    
}
