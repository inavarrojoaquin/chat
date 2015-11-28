package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.UserLoginEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.Date;
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
public class LoginProfileAction extends Action{

    //final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milliseconds per day
    //final long MILLSECS_PER_10_DAYS = MILLSECS_PER_DAY * 10; //Milliseconds per 10 days
    //final long MILLSECS_PER_10_DAYS = 60000 * 2; //for test in 1 minute = 60000 milliseconds
    
//    Date date = new Date();
//    long actualDate = date.getTime(); //actual date in milliseconds
//    long initDate = userLogin.getDatetimeOfAccessStart().getTime(); //the last login date in milliseconds
//    long dateDiff = actualDate - initDate; //difference of time in milliseconds
//
//    if (dateDiff > MILLSECS_PER_10_DAYS) {}

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("LoginProfileAction:execute");  
        
        HttpSession session = request.getSession(false); 
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "LoginProfileAction-Session 1: " + session);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "LoginProfileAction-Session 2: " + session.getAttribute("profile"));
        Logger.getLogger(getClass().getName()).log(Level.INFO, "LoginProfileAction-Session ID: " + session.getId());
        
        if(session.getAttribute("profile") != null){            
            this.getForm().setItem("profile", session.getAttribute("profile"));
            this.gotoPage("/template/user/home.jsp", request, response);            
        }else{
            String login = (String) this.getForm().getItem("userName");
            String password = (String) this.getForm().getItem("password");
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "LoginProfileAction-Param: {0}", login);
            Logger.getLogger(getClass().getName()).log(Level.INFO, "LoginProfileAction-Param: {0}", password);
            
            Client client = ClientBuilder.newClient();
            
            /**If exist user insert user_login register in the database*/
            Form form = new Form();
            form.param("login", login);
            form.param("password", password);
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "LoginProfileAction-PRE llamado a PROFILE");
            
            WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles/find/login/password");
            Invocation profileInvocation = profileTarget.request().buildPost(Entity.form(form));
            Response profileResponse = profileInvocation.invoke();
            
            Logger.getLogger(getClass().getName()).log(Level.INFO, "LoginProfileAction-POS llamado a PROFILE: " + profileResponse.getStatus());
            
            if(profileResponse.getStatusInfo().getReasonPhrase().equals("OK")){
                ProfileEntity profile = profileResponse.readEntity(new GenericType<ProfileEntity>(){});
                
                System.out.println("UserLogin created.");

                session.setAttribute("profile", profile);
                
                this.getForm().setItem("profile", profile);
                
                Logger.getLogger(getClass().getName()).log(Level.INFO, "LoginProfileAction-SessionAttribute: {0}", session.getAttribute("profile"));
                
                this.gotoPage("/template/user/home.jsp", request, response);
            }
            else {
                request.setAttribute("response", "User not found or password incorrect...");
                this.gotoPage("/template/login.jsp", request, response);
            }
        }
    }
}
