package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.mvc.actions.Action;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class SearchUsersActivesAction extends Action{
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("SearchUsersActivesAction:execute");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Client client = ClientBuilder.newClient();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "SearchUsersActivesAction-PRE llamado a SEARCHUSERACTIVES");
        
        /**search users actives*/
        WebTarget searchUsersActivesTarget = client.target("http://localhost:8080/chat/webresources/profiles/usersLogin/actives");        
        Invocation searchUsersActivesInvocation = searchUsersActivesTarget.request().buildGet();
        Response searchUsersActivesResponse = searchUsersActivesInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "SearchUsersActivesAction-POS llamado a SEARCHUSERACTIVES: " + searchUsersActivesResponse.getStatus());

        if(searchUsersActivesResponse.getStatus() == 200){
            String json = searchUsersActivesResponse.readEntity(String.class);
        
            response.getWriter().write(json);
        }
    }
}
