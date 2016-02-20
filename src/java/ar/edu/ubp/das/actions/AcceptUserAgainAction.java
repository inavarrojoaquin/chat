package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.UserLoginEntity;
import ar.edu.ubp.das.mvc.actions.Action;
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
public class AcceptUserAgainAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("AcceptUserAgainAction:execute");
        
        String policyId = (String) this.getForm().getItem("policyId");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "AcceptUserAgainAction-Param: {0}", policyId);

        Client client = ClientBuilder.newClient();

        Logger.getLogger(getClass().getName()).log(Level.INFO, "AcceptUserAgainAction-PRE llamado a POLICY");
        /**Delete policy by policyId*/
        Form policyForm = new Form();
        policyForm.param("policyId", policyId);
        
        WebTarget policyTarget = client.target("http://localhost:8080/chat/webresources/roomaccesspolicy/update/policy/id");        
        Invocation policyInvocation = policyTarget.request().buildPost(Entity.form(policyForm));
        Response policyResponse = policyInvocation.invoke();

        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetInvitationListAction-POS llamado a POLICY: " + policyResponse.getStatus());
        
        System.out.println("POLICY ID UPDATED SECCESSFULL");
    }
    
}
