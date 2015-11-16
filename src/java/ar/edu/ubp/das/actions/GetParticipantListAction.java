package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
import ar.edu.ubp.das.entities.RoomAccessPolicyEntity;
import ar.edu.ubp.das.mvc.actions.Action;
import ar.edu.ubp.das.mvc.daos.DaoFactory;
import java.util.LinkedList;
import java.util.List;
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
public class GetParticipantListAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetParticipantsListAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");
        String profileType = (String) this.getForm().getItem("profileType");
        String userAccessId = (String) this.getForm().getItem("userAccessId");
        
        List<ProfileEntity> usersActivesList;
        
        if(roomId != null){
            Client client = ClientBuilder.newClient();
            WebTarget usersActivesTarget = client.target("http://localhost:8080/chat/webresources/profiles/room/" + roomId + "/actives");        
            Invocation usersActivesInvocation = usersActivesTarget.request().buildGet();
            Response usersActivesResponse = usersActivesInvocation.invoke();
            usersActivesList = usersActivesResponse.readEntity(new GenericType<List<ProfileEntity>>(){});       
            
        }else {
            Client client = ClientBuilder.newClient();
            WebTarget usersActivesTarget = client.target("http://localhost:8080/chat/webresources/profiles/users/actives");        
            Invocation usersActivesInvocation = usersActivesTarget.request().buildGet();
            Response usersActivesResponse = usersActivesInvocation.invoke();
            usersActivesList = usersActivesResponse.readEntity(new GenericType<List<ProfileEntity>>(){});       
        }
        
        this.getForm().setItem("participantsList", usersActivesList);
        this.getForm().setItem("roomId", roomId);
        this.getForm().setItem("profileType", profileType);
        this.getForm().setItem("userAccessId", userAccessId);
        this.gotoPage("/template/user/participantList.jsp", request, response);
        
    }
    
}
