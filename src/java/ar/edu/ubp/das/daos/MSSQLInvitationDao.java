package ar.edu.ubp.das.daos;

import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import ar.edu.ubp.das.mvc.daos.MSSQLDao;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import static java.sql.Types.INTEGER;
import java.util.List;

/**
 *
 * @author Febo
 */
public class MSSQLInvitationDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm invitation = new DynaActionForm();
        
        invitation.setItem("id", result.getInt("id"));
        invitation.setItem("room", result.getInt("room"));
        invitation.setItem("sender", result.getInt("sender"));
        invitation.setItem("receiver", result.getInt("receiver"));
        invitation.setItem("state", result.getString("state"));
        
        return invitation;
    }

    @Override
    public void insert(DynaActionForm form) throws Exception {
        this.setStatement("proc_InsertInvitation(?,?,?,?,?)");
        CallableStatement statement = this.getStatement();

        statement.registerOutParameter("id",INTEGER);
        statement.setInt("room", (int) form.getItem("room"));
        statement.setInt("sender", (int) form.getItem("sender"));
        statement.setInt("receiver", (int) form.getItem("receiver"));
        statement.setString("state", (String) form.getItem("state"));
        
        this.executeUpdate();
        form.setItem("id", statement.getInt("id"));
    }

    @Override
    public void update(DynaActionForm form) throws Exception {
        this.setStatement("proc_UpdateInvitation(?,?,?,?,?)");
        CallableStatement statement = this.getStatement();

        statement.setInt("id", (int) form.getItem("id"));
        statement.setInt("room", (int) form.getItem("room"));
        statement.setInt("sender", (int) form.getItem("sender"));
        statement.setInt("receiver", (int) form.getItem("receiver"));
        statement.setString("state", (String) form.getItem("state"));
        
        this.executeUpdate();
    }

    @Override
    public void delete(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DynaActionForm> select(DynaActionForm form) throws Exception {
        String selector = (String) form.getItem("selector");
        
        if(selector.equals("byId")){
            this.setStatement("proc_SelectInvitationById(?)");
            this.getStatement().setInt("id", (int) form.getItem("id"));
        }
        
        else if(selector.equals("bySender")){
            this.setStatement("proc_SelectInvitationBySender(?)");
            this.getStatement().setInt("sender", (int) form.getItem("sender"));
        }
        
        else if(selector.equals("byReceiver")){
            this.setStatement("proc_SelectInvitationByReceiver(?)");
            this.getStatement().setInt("receiver", (int) form.getItem("receiver"));
        }
        
        else {
            this.setStatement("proc_SelectInvitations()");
        }
        
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
