package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.MessageEntity;
import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.RoomEntity;
import ar.edu.ubp.das.entities.UserLoginEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import ar.edu.ubp.das.mvc.daos.Dao;
import ar.edu.ubp.das.mvc.daos.DaoFactory;
import java.io.PrintWriter;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class LoginProfileAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("LoginProfileAction:execute");  
        
        HttpSession session = request.getSession(); 
        
        if(session.getAttribute("sessionprofile") != null){
            this.getForm().setItem("profile", session.getAttribute("sessionprofile"));
            this.gotoPage("/home.jsp", request, response);
        }else{
            String login = (String) this.getForm().getItem("userName");

            Client client = ClientBuilder.newClient();

            WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles/login/" + login);        
            Invocation profileInvocation = profileTarget.request().buildGet();
            Response res = profileInvocation.invoke();

            if(res.getStatusInfo().getReasonPhrase().equals("OK")){
                ProfileEntity profile = res.readEntity(new GenericType<ProfileEntity>(){});
                session.setAttribute("sessionprofile", profile);
                session.setMaxInactiveInterval(30*60);
                this.getForm().setItem("profile", profile);
                this.gotoPage("/home.jsp", request, response);
            }else {
                request.setAttribute("response", "User not found...");
                this.gotoPage("/login.jsp", request, response);
            }
        }
    }
}
