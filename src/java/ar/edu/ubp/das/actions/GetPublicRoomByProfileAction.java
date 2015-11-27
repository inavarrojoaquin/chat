package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.RoomEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import java.util.List;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class GetPublicRoomByProfileAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetPublicRoomByProfileAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetPublicRoomByProfileAction-Param: {0}", profileId);
        
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("id", profileId);
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetPublicRoomByProfileAction-PRE llamado a PUBLICROOMS");
        
        WebTarget publicRoomsTarget = client.target("http://localhost:8080/chat/webresources/rooms/public/participant/profile");        
        Invocation publicRoomsInvocation = publicRoomsTarget.request().buildPost(Entity.form(form));
        Response publicRoomsResponse = publicRoomsInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetPublicRoomByProfileAction-POS llamado a PUBLICROOMS: " + publicRoomsResponse.getStatus());
        
        if(publicRoomsResponse.getStatus() == 200){
            List<RoomEntity> publicRoomsList = publicRoomsResponse.readEntity(new GenericType<List<RoomEntity>>(){});
            
            if(publicRoomsList != null){
                this.getForm().setItem("publicParticipateRooms", publicRoomsList);
                this.getForm().setItem("profileId", profileId);
                this.gotoPage("/template/user/publicRoomList.jsp", request, response);
            }else {
                response.getWriter().println("Empty public rooms");
            }
        }
    }
    
}
