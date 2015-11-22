package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.UserAccessEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class LeaveGroupAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("LeaveGroupAction:execute");
        
        String userAccessId =  (String) this.getForm().getItem("userAccessId");
        
        Client client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("id", userAccessId);
        /**Get user acces*/
        WebTarget usersActivesTarget = client.target("http://localhost:8080/chat/webresources/useraccess/find/id");        
        Invocation usersActivesInvocation = usersActivesTarget.request().buildPost(Entity.form(form));
        Response usersActivesResponse = usersActivesInvocation.invoke();
        
        if(usersActivesResponse.getStatusInfo().getReasonPhrase().equals("OK")){
            UserAccessEntity userAccessEntity = usersActivesResponse.readEntity(new GenericType<UserAccessEntity>(){});
            
            /**User acess terminate session*/
            WebTarget usersActivesTarget1 = client.target("http://localhost:8080/chat/webresources/useraccess/id/terminate");        
            Invocation usersActivesInvocation1 = usersActivesTarget1.request().buildPut(Entity.json(userAccessEntity));
            Response usersActivesResponse1 = usersActivesInvocation1.invoke();

            if(usersActivesResponse1.getStatusInfo().getReasonPhrase().equals("OK")){
                this.gotoPage("/index.jsp?action=LoginProfile", request, response);
            }
        }
    }
    
}
