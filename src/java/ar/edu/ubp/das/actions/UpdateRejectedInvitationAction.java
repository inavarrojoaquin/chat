package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.mvc.actions.Action;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class UpdateRejectedInvitationAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("UpdateRejectedInvitationAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateRejectedInvitationAction-Param: {0}", roomId);
        
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("id", roomId);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateRejectedInvitationAction-PRE llamado a PROFILE");
        
        /**Get rejected invitations */
        WebTarget profileTarget = client.target("http://localhost:8080/chat/webresources/profiles/rejected/invitation/by/room");        
        Invocation profileInvocation = profileTarget.request().buildPost(Entity.form(form));
        Response profileResponse = profileInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateRejectedInvitationAction-POS llamado a PROFILE: " + profileResponse.getStatus());
        
        if(profileResponse.getStatus() == 200){
            String json = profileResponse.readEntity(String.class);
        
            System.out.println("Invitation JSON: " + json);

            if(!json.equals("[]")){ 
                response.getWriter().write(json);
            }
        }
    }
}
//String personJSONData = "[{\"id\":2,\"login\":\"nico\",\"password\":\"nico\",\"type\":\"USER\"},{\"id\":4,\"login\":\"orlando\",\"password\":\"orlando\",\"type\":\"USER\"}]";

//                JsonReader jsonReader = Json.createReader(new StringReader(json));
//                JsonArray jsonArray = jsonReader.readArray();
//
//                jsonReader.close();
//
//                for (JsonValue j : jsonArray) {
//                    System.out.println("Json1: " + j.toString());
//                    jsonReader = Json.createReader(new StringReader(j.toString()));
//                    JsonObject object = jsonReader.readObject();
//                    jsonReader.close();
//
//                    Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateRejectedInvitationAction-PRE llamado a INVITATION");
//                    System.out.println("Json1-PROFILEID: " + object.get("id"));    
//
//                    WebTarget invitationTarget = client.target("http://localhost:8080/chat/webresources/invitations/delete/profile/" + Integer.toString(object.getInt("id")) + "/room/" + roomId);
//                    Invocation invitationInvocation = invitationTarget.request().buildDelete();
//                    Response invitationResponse = invitationInvocation.invoke();
//
//                    Logger.getLogger(getClass().getName()).log(Level.INFO, "UpdateRejectedInvitationAction-POS llamado a INVITATION: " + invitationResponse.getStatus());
//
//                    System.out.println("Invitation rejected deleted: " + invitationResponse.getStatusInfo().getReasonPhrase());
//
//                }
