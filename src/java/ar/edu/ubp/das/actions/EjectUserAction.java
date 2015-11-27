package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.RoomAccessPolicyEntity;
import ar.edu.ubp.das.entities.UserAccessEntity;
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
public class EjectUserAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("EjectUserAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");
        String profileId = (String) this.getForm().getItem("profileId");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "EjectUserAction-Param: {0}", roomId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "EjectUserAction-Param: {0}", profileId);
        
        /**Create userAccesPolicyEntity with attributes*/
        RoomAccessPolicyEntity policyEntity = new RoomAccessPolicyEntity();
        policyEntity.setRoom(Integer.parseInt(roomId));
        policyEntity.setProfile(Integer.parseInt(profileId));
        policyEntity.setPolicy("ejected");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "EjectUserAction-PRE llamado a POLICY");
        
        Client client = ClientBuilder.newClient();
        /**Add policy for particular profile*/
        WebTarget policyTarget = client.target("http://localhost:8080/chat/webresources/roomaccesspolicy");        
        Invocation policyInvocation = policyTarget.request().buildPost(Entity.json(policyEntity));
        Response policyResponse = policyInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "EjectUserAction-POS llamado a POLICY: "+policyResponse.getStatus());
        
        if(policyResponse.getStatus() == 200){
            Logger.getLogger(getClass().getName()).log(Level.INFO, "EjectUserAction-PRE llamado a USERACCESS");
            
            /**Get last user access by profile*/
            Form form = new Form();
            form.param("id", profileId);
            WebTarget usersActivesTarget = client.target("http://localhost:8080/chat/webresources/useraccess/find/last/profile");
            Invocation usersActivesInvocation = usersActivesTarget.request().buildPost(Entity.form(form));
            Response usersActivesResponse = usersActivesInvocation.invoke();
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "EjectUserAction-POS llamado a USERACCESS: " + usersActivesResponse.getStatus());

            if(usersActivesResponse.getStatusInfo().getReasonPhrase().equals("OK")){
                UserAccessEntity userAccessEntity = usersActivesResponse.readEntity(new GenericType<UserAccessEntity>(){});

                Logger.getLogger(getClass().getName()).log(Level.INFO, "EjectUserAction-PRE llamado a FINISHSESSION");
                /**Finish session for one user access*/
                WebTarget usersActivesTarget1 = client.target("http://localhost:8080/chat/webresources/useraccess/id/terminate");        
                Invocation usersActivesInvocation1 = usersActivesTarget1.request().buildPut(Entity.json(userAccessEntity));
                Response usersActivesResponse1 = usersActivesInvocation1.invoke();
                
                Logger.getLogger(getClass().getName()).log(Level.INFO, "EjectUserAction-POS llamado a FINISHSESSION: " + usersActivesResponse1.getStatus());
            }
        }
    }
    
}
