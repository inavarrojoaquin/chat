package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
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
        System.out.println("NewProfileAction");
        PrintWriter out = response.getWriter();
        
        ProfileEntity profile = new ProfileEntity();
        profile.setLogin((String) this.getForm().getItem("login"));
        profile.setPassword((String) this.getForm().getItem("password"));

//        JsonObject profile = Json.createObjectBuilder()
//                .add("login", login)
//                .add("password", password).build();
//        
        Client client = ClientBuilder.newClient();
        
        WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles");        
        Invocation profilesInvocation = profileTarget.request().buildPost(Entity.json(profile));
        Response res = profilesInvocation.invoke();
        
        if(res.getStatus() != Response.Status.CONFLICT.getStatusCode()){
            ProfileEntity entity = res.readEntity(new GenericType<ProfileEntity>(){});
            
        }else {
            out.println("El usuario ya existe...");
        }
        
        
        
    }
}
