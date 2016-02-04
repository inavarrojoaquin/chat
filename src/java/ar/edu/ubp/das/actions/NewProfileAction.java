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
       
        if(this.getForm().getItem("password").equals(this.getForm().getItem("confirmPassword"))){
            ProfileEntity profile = new ProfileEntity();
            profile.setLogin((String) this.getForm().getItem("userName"));
            profile.setPassword((String) this.getForm().getItem("password"));

            Client client = ClientBuilder.newClient();

            WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles");        
            Invocation profilesInvocation = profileTarget.request().buildPost(Entity.json(profile));
            Response res = profilesInvocation.invoke();
            
            if(res.getStatus() != Response.Status.CONFLICT.getStatusCode()){
                profile = res.readEntity(new GenericType<ProfileEntity>(){});
                request.setAttribute("response", "Registro exitoso, realice el login");
            }else {
                request.setAttribute("error", "Error: El usuario ya existe...");
            }
        }else{
            request.setAttribute("error", "Error: Las contraseñas ingresadas son diferentes. Vuelva a intentar el registro");
        }
        this.gotoPage("/template/login.jsp", request, response);
    }
}
