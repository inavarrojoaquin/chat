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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class LoginProfileAction extends Action{

    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milliseconds per day
    final long MILLSECS_PER_10_DAYS = MILLSECS_PER_DAY * 10; //Milliseconds per 10 days
    //final long MILLSECS_PER_10_DAYS = 60000; //for test in 1 minute = 60000 milliseconds
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("LoginProfileAction:execute");  
        
        HttpSession session = request.getSession(); 
        
        if(session.getAttribute("sessionprofile") != null){
            this.getForm().setItem("profile", session.getAttribute("sessionprofile"));
            this.gotoPage("/template/user/home.jsp", request, response);            
        }else{
            String userName = request.getParameter("userName"); //Is not null when arrive from email validator 
            String validateFromEmail =request.getParameter("sessionValidation"); //Is not null when arrive from email validator 
            
            String login = this.getForm().getItem("userName") != null ? (String) this.getForm().getItem("userName") : userName;
            
            Client client = ClientBuilder.newClient();

            /**Get profile*/
            WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles/login/" + login);        
            Invocation profileInvocation = profileTarget.request().buildGet();
            Response profileResponse = profileInvocation.invoke();

            if(profileResponse.getStatusInfo().getReasonPhrase().equals("OK")){
                ProfileEntity profile = profileResponse.readEntity(new GenericType<ProfileEntity>(){});
                
                /**Is null when arrive from email validator */
                String password = this.getForm().getItem("password") != null ? (String) this.getForm().getItem("password") : profile.getPassword();
                
                if(password.equals(profile.getPassword())){
                    UserLoginEntity userLogin = new UserLoginEntity();
                    userLogin.setProfile(profile.getId());

                    boolean flag = false;

                    //Enable block for implement send email
//                if(validateFromEmail == null){
//                    /**Get the last access for profile*/
//                    WebTarget lastLoginTarget = client.target("http://localhost:8080/chat/webresources/userslogins/lastlogin/profile/" + profile.getId());
//                    Invocation lastLoginInvocation = lastLoginTarget.request().buildGet();
//                    Response lastLoginResponse = lastLoginInvocation.invoke();
//                    System.out.println("Last Login status: "+lastLoginResponse.getStatus());
//                    if (lastLoginResponse.getStatus() == Response.Status.OK.getStatusCode()) {
//                        userLogin = lastLoginResponse.readEntity(new GenericType<UserLoginEntity>() {});
//
//                        Date date = new Date();
//                        long actualDate = date.getTime(); //actual date in milliseconds
//                        long initDate = userLogin.getDatetimeOfAccessStart().getTime(); //the last login date in milliseconds
//                        long dateDiff = actualDate - initDate; //difference of time in milliseconds
//                        System.out.println("DateDiff: "+dateDiff);  
//                        if (dateDiff > MILLSECS_PER_10_DAYS) {
//                            WebTarget profileAuthTarget = client.target("http://localhost:8080/chat/webresources/userslogins/auth/profile/" + profile.getLogin());
//                            Invocation profileAuthInvocation = profileAuthTarget.request().buildGet();
//                            Response profileAuthResponse = profileAuthInvocation.invoke();
//
//                            if (profileAuthResponse.getStatusInfo().getReasonPhrase().equals("OK")) {
//                                System.out.println("Send email for validate account");
//                                flag = true;
//                                request.setAttribute("response", "Your account has expired. See your email account and try it again...");
//                                this.gotoPage("/template/login.jsp", request, response);
//                            } else {
//                                flag = true;
//                                request.setAttribute("response", "Error in the send email. Try it egain...");
//                                this.gotoPage("/template/login.jsp", request, response);
//                            }
//                        }
//                    }
//                }
                    if (!flag) {
                        /**
                         * Save new access for profile
                         */
                        WebTarget userLoginTarget = client.target("http://localhost:8080/chat/webresources/userslogins");
                        Invocation userLoginInvocation = userLoginTarget.request().buildPost(Entity.json(userLogin));
                        Response userLoginResponse = userLoginInvocation.invoke();

                        if (userLoginResponse.getStatusInfo().getReasonPhrase().equals("OK")) {
                            System.out.println("Create UserLogin");

                            session.setAttribute("sessionprofile", profile);
                            session.setMaxInactiveInterval(30 * 60);
                            this.getForm().setItem("profile", profile);
                            this.gotoPage("/template/user/home.jsp", request, response);

                            if (profile.getType().equals("admin")) {
                                this.gotoPage("/template/admin/home.jsp", request, response);
                            }
                        }
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
