package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.RoomAccessPolicyEntity;
import ar.edu.ubp.das.entities.UserAccessEntity;
import ar.edu.ubp.das.mvc.actions.Action;
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
        String userAccessId = (String) this.getForm().getItem("userAccessId");
        
        /**Create userAccesPolicyEntity with attributes*/
        RoomAccessPolicyEntity policyEntity = new RoomAccessPolicyEntity();
        policyEntity.setRoom(Integer.parseInt(roomId));
        policyEntity.setProfile(Integer.parseInt(profileId));
        policyEntity.setPolicy("ejected");
        
        Client client = ClientBuilder.newClient();
        /**Add policy for particular profile*/
        WebTarget policyTarget = client.target("http://localhost:8080/chat/webresources/roomaccesspolicy");        
        Invocation policyInvocation = policyTarget.request().buildPost(Entity.json(policyEntity));
        Response policyResponse = policyInvocation.invoke();
        
        /**Get user access*/
        Form form = new Form();
        form.param("id", userAccessId);
        WebTarget usersActivesTarget = client.target("http://localhost:8080/chat/webresources/useraccess/find/id");
        Invocation usersActivesInvocation = usersActivesTarget.request().buildPost(Entity.form(form));
        Response usersActivesResponse = usersActivesInvocation.invoke();
        
        if(usersActivesResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            UserAccessEntity userAccessEntity = usersActivesResponse.readEntity(new GenericType<UserAccessEntity>(){});
            
            /**Finish session for one user access*/
            WebTarget usersActivesTarget1 = client.target("http://localhost:8080/chat/webresources/useraccess/id/terminate");        
            Invocation usersActivesInvocation1 = usersActivesTarget1.request().buildPut(Entity.json(userAccessEntity));
            Response usersActivesResponse1 = usersActivesInvocation1.invoke();
        }
    }
    
}
