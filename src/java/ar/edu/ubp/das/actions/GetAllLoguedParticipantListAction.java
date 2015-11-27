package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.entities.ProfileEntity;
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
public class GetAllLoguedParticipantListAction extends Action{
    
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("GetAllLoguedParticipantListAction:execute");
        
        String roomId = (String) this.getForm().getItem("roomId");       
        String profileType = (String) this.getForm().getItem("profileType");
        String userAccessId = (String) this.getForm().getItem("userAccessId");
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetAllLoguedParticipantListAction-Param: {0}", roomId);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetAllLoguedParticipantListAction-Param: {0}", profileType);
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetAllLoguedParticipantListAction-Param: {0}", userAccessId);
        
        Client client = ClientBuilder.newClient();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetAllLoguedParticipantListAction-PRE llamado a USERSLOGINaCTIVES");
        
        WebTarget usersLoginActivesTarget = client.target("http://localhost:8080/chat/webresources/profiles/usersLogin/actives");        
        Invocation usersLoginActivesInvocation = usersLoginActivesTarget.request().buildGet();
        Response usersLoginActivesResponse = usersLoginActivesInvocation.invoke();
        
        Logger.getLogger(getClass().getName()).log(Level.INFO, "GetAllLoguedParticipantListAction-POS llamado a USERSLOGINaCTIVES: " + usersLoginActivesResponse.getStatus());

        if(usersLoginActivesResponse.getStatus() == 200){
            List<ProfileEntity> usersActivesList = usersLoginActivesResponse.readEntity(new GenericType<List<ProfileEntity>>(){});

            this.getForm().setItem("participantsList", usersActivesList);
            this.getForm().setItem("roomId", roomId);
            this.getForm().setItem("profileType", profileType);
            this.getForm().setItem("userAccessId", userAccessId);
            this.gotoPage("/template/user/participantList.jsp", request, response);
        }
        
    }
    
}
