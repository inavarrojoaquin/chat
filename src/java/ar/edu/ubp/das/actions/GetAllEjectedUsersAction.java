package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.RoomAccessPolicyComplexEntity;
import ar.edu.ubp.das.entities.RoomAccessPolicyEntity;
import ar.edu.ubp.das.entities.UserLoginEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.List;
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
public class GetAllEjectedUsersAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetAllEjectedUsersAction:execute");
        
        Client client = ClientBuilder.newClient();

        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetAllEjectedUsersAction-PRE llamado a POLICY");
        
        WebTarget policyTarget = client.target("http://localhost:8080/chat/webresources/roomaccesspolicy");        
        Invocation policyInvocation = policyTarget.request().buildGet();
        Response policyResponse = policyInvocation.invoke();

        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetAllEjectedUsersAction-POS llamado a POLICY: " + policyResponse.getStatus());
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetAllEjectedUsersAction-PRE llamado a INVITATION");
            
        if(policyResponse.getStatusInfo().getStatusCode() == 200){
            List<RoomAccessPolicyComplexEntity> ejectedUserList = policyResponse.readEntity(new GenericType<List<RoomAccessPolicyComplexEntity>>(){});
            
            this.getForm().setItem("ejectedUserList", ejectedUserList);
            this.gotoPage("/template/user/ejectedUserList.jsp", request, response);
        }
            
    }
    
}
