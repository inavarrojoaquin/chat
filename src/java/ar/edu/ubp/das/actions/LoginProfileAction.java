package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.UserLoginEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.Date;
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
        
        HttpSession session = request.getSession(); 
        
        if(session.getAttribute("sessionprofile") != null){
            this.getForm().setItem("profile", session.getAttribute("sessionprofile"));
            this.gotoPage("/template/user/home.jsp", request, response);            
        }else{
            String login = (String) this.getForm().getItem("userName");
            String password = (String) this.getForm().getItem("password");
            
            Client client = ClientBuilder.newClient();

            /**Get profile*/
            Form form = new Form();
            form.param("login", login);
            WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles/find/login");
            Invocation profileInvocation = profileTarget.request().buildPost(Entity.form(form));
            Response profileResponse = profileInvocation.invoke();
           
            if(profileResponse.getStatusInfo().getReasonPhrase().equals("OK")){
                ProfileEntity profile = profileResponse.readEntity(new GenericType<ProfileEntity>(){});
                
                if(password.equals(profile.getPassword())){
                    UserLoginEntity userLogin = new UserLoginEntity();
                    userLogin.setProfile(profile.getId());

                    /** Create new access for profile*/
                    WebTarget userLoginTarget = client.target("http://localhost:8080/chat/webresources/userslogins");
                    Invocation userLoginInvocation = userLoginTarget.request().buildPost(Entity.json(userLogin));
                    Response userLoginResponse = userLoginInvocation.invoke();

                    if (userLoginResponse.getStatusInfo().getReasonPhrase().equals("OK")) {
                        System.out.println("UserLogin created.");

                        session.setAttribute("sessionprofile", profile);
                        session.setMaxInactiveInterval(30 * 60);
                        
                        this.getForm().setItem("profile", profile);
                        this.gotoPage("/template/user/home.jsp", request, response);
                    }
                } else {
                    request.setAttribute("response", "Password incorrect.");
                    this.gotoPage("/template/login.jsp", request, response);
                }
             }else {
                request.setAttribute("response", "User not found.");
                this.gotoPage("/template/login.jsp", request, response);
            }
        }
    }
}
