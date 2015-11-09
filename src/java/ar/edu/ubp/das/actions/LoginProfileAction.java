package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.UserLoginEntity;
import ar.edu.ubp.das.mvc.actions.Action;
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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("LoginProfileAction:execute");  
        
        HttpSession session = request.getSession(); 
        
        if(session.getAttribute("sessionprofile") != null){
            this.getForm().setItem("profile", session.getAttribute("sessionprofile"));
            this.gotoPage("/template/user/home.jsp", request, response);
        }else{
            String login = (String) this.getForm().getItem("userName");

            Client client = ClientBuilder.newClient();

            WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles/login/" + login);        
            Invocation profileInvocation = profileTarget.request().buildGet();
            Response profileResponse = profileInvocation.invoke();

            if(profileResponse.getStatusInfo().getReasonPhrase().equals("OK")){
                ProfileEntity profile = profileResponse.readEntity(new GenericType<ProfileEntity>(){});
                session.setAttribute("sessionprofile", profile);
                session.setMaxInactiveInterval(30*60);
                
                UserLoginEntity userLogin = new UserLoginEntity();
                userLogin.setProfile(profile.getId());
                WebTarget userLoginTarget = client.target("http://localhost:8080/chat/webresources/userslogins");        
                Invocation userLoginInvocation = userLoginTarget.request().buildPost(Entity.json(userLogin));
                Response userLoginResponse = userLoginInvocation.invoke();
                
                if(userLoginResponse.getStatusInfo().getReasonPhrase().equals("OK")){
                    System.out.println("Create UserLogin");
                    
                    this.getForm().setItem("profile", profile);
                    if(profile.getType().equals("admin")){
                        this.gotoPage("/template/admin/home.jsp", request, response);
                    }else{
                        this.gotoPage("/template/user/home.jsp", request, response);
                    }
                }
            }else {
                request.setAttribute("response", "User not found...");
                this.gotoPage("/template/login.jsp", request, response);
            }
        }
    }
}
