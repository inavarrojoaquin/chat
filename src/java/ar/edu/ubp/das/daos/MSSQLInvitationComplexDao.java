package ar.edu.ubp.das.daos;

import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import ar.edu.ubp.das.mvc.daos.MSSQLDao;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Febo
 */
public class MSSQLInvitationComplexDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm invitation = new DynaActionForm();
        
        invitation.setItem("id", result.getInt("id"));
        invitation.setItem("room", result.getInt("room"));
        invitation.setItem("sender", result.getInt("sender"));
        invitation.setItem("receiver", result.getInt("receiver"));
        invitation.setItem("state", result.getString("state"));
        invitation.setItem("roomName", result.getString("roomName"));
        invitation.setItem("senderName", result.getString("senderName"));
        
        return invitation;
    }

    @Override
    public void insert(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DynaActionForm> select(DynaActionForm form) throws Exception {
        String selector = (String) form.getItem("selector");
        
        if(selector.equals("byReceiver")){
            this.setStatement("proc_SelectInvitationByReceiver(?)");
            this.getStatement().setInt("receiver", (int) form.getItem("receiver"));
        }
        
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
