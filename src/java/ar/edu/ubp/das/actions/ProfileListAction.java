package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class ProfileListAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("ProfileListAction");
        PrintWriter out = response.getWriter();
        
        DynaActionForm form = this.getForm();
        Client client = ClientBuilder.newClient();
        
        WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles");
        Invocation profilesInvocation = profileTarget.request().buildGet();
        Response res = profilesInvocation.invoke();

        List<ProfileEntity> profiles = res.readEntity(new GenericType<List<ProfileEntity>>(){});
        form.setItem("profileList", profiles);
        
        this.gotoPage("/list.jsp", request, response);
    }
    
}
