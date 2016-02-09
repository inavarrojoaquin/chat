package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class NewProfileAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {        
        System.out.println("NewProfileAction:execute");
        
        String userName = (String) this.getForm().getItem("userName");
        String password = (String) this.getForm().getItem("password");
        String confirmPassword = (String) this.getForm().getItem("confirmPassword");
       
        if(password.equals(confirmPassword)){
            ProfileEntity profile = new ProfileEntity();
            profile.setLogin(userName);
            profile.setPassword(password);

            Client client = ClientBuilder.newClient();

            WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles");        
            Invocation profilesInvocation = profileTarget.request().buildPost(Entity.json(profile));
            Response res = profilesInvocation.invoke();
            
            if(res.getStatus() != Response.Status.CONFLICT.getStatusCode()){
                profile = res.readEntity(new GenericType<ProfileEntity>(){});
                request.setAttribute("response", "Successful registration. Please login.");
            }else {
                request.setAttribute("error", "Error: The user already exists...");
            }
        }else{
            request.setAttribute("error", "Error: The passwords are differents. Try again...");
        }
        request.setAttribute("userNameNew", userName);
        this.gotoPage("/template/login.jsp", request, response);
    }
}
