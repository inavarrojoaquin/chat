package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.RoomComplexEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Febo
 */
public class GetPublicRoomAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetPublicRoomAction:execute");
        
        String profileId = (String) this.getForm().getItem("profileId");        
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetPublicRoomAction-Param: {0}", profileId);
        
        Client client = ClientBuilder.newClient();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetPublicRoomAction-PRE llamado a PUBLICROOM");
        
        WebTarget publicRoomsTarget = client.target("http://localhost:8080/chat/webresources/rooms/type/public");        
        Invocation publicRoomsInvocation = publicRoomsTarget.request().buildGet();
        Response publicRoomsResponse = publicRoomsInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetPublicRoomAction-POS llamado a PUBLICROOM: " + publicRoomsResponse.getStatus());
        
        List<RoomComplexEntity> publicRoomsList = publicRoomsResponse.readEntity(new GenericType<List<RoomComplexEntity>>(){});
        
        if(publicRoomsList != null){
            this.getForm().setItem("publicRooms", publicRoomsList);
            this.getForm().setItem("profileId", profileId);
            this.gotoPage("/template/user/publicRoomList.jsp", request, response);
        }else {
            response.getWriter().println("Empty public rooms");
        }
    }
    
}
