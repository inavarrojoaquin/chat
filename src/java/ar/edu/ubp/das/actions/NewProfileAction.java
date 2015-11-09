package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class NewProfileAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {        
        System.out.println("NewProfileAction:execute");
       
        ProfileEntity profile = new ProfileEntity();
        profile.setLogin((String) this.getForm().getItem("userName"));
        profile.setPassword((String) this.getForm().getItem("password"));

//        JsonObject profile = Json.createObjectBuilder()
//                .add("login", login)
//                .add("password", password).build();
//        
        Client client = ClientBuilder.newClient();
        
        WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles");        
        Invocation profilesInvocation = profileTarget.request().buildPost(Entity.json(profile));
        Response res = profilesInvocation.invoke();
        
        String resp;
        if(res.getStatus() != Response.Status.CONFLICT.getStatusCode()){
            resp = "Registro exitoso, realice el login";
        }else {
            resp = "El usuario ya existe...";
        }
        request.setAttribute("response", resp);
        this.gotoPage("/template/login.jsp", request, response);
    }
}