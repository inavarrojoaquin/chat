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
public class MSSQLRoomAccessPolicyDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm form = new DynaActionForm();
        
        form.setItem("id", result.getInt("id"));
        form.setItem("room", result.getInt("room"));
        form.setItem("profile", result.getInt("profile"));
        form.setItem("policy", result.getString("policy"));
        
        return form;
    }

    @Override
    public void insert(DynaActionForm form) throws Exception {
        this.setStatement("proc_InsertRoomAccessPolicy(?,?,?,?)");
        CallableStatement statement = this.getStatement();
        
        statement.registerOutParameter("id",INTEGER);
        statement.setInt("room", (int) form.getItem("room"));
        statement.setInt("profile", (int) form.getItem("profile"));
        statement.setString("policy", (String) form.getItem("policy"));
        
        this.executeUpdate();
        form.setItem("id", statement.getInt("id"));
    }

    @Override
    public void update(DynaActionForm form) throws Exception {
        this.setStatement("proc_UpdateRoomAccessPolicy(?,?,?,?)");
        CallableStatement statement = this.getStatement();
        
        statement.setInt("id", (int) form.getItem("id"));
        statement.setInt("room", (int) form.getItem("room"));
        statement.setInt("profile", (int) form.getItem("profile"));
        statement.setString("policy", (String) form.getItem("policy"));
        
        this.executeUpdate();
    }

    @Override
    public void delete(DynaActionForm form) throws Exception {
        this.setStatement("proc_DeleteRoomsAccessPolicyByProfile(?)");
        CallableStatement statement = this.getStatement();
        statement.setInt("profile", (int) form.getItem("profile"));
        
        this.executeUpdate();
    }

    @Override
    public List<DynaActionForm> select(DynaActionForm form) throws Exception {
        String selector = (String) form.getItem("selector");
        
        if(selector.equals("byId")){
            this.setStatement("proc_SelectRoomAccessPolicyById(?)");
            this.getStatement().setInt("id", (int) form.getItem("id"));
        }
        
        else if(selector.equals("byRoom")){
            this.setStatement("proc_SelectRoomAccessPolicyByRoom(?)");
            this.getStatement().setInt("room", (int) form.getItem("room"));
        }
        
        else if(selector.equals("byRoomAndProfile")){
            this.setStatement("proc_SelectAccessPolicyByRoomAndProfile(?,?)");
            this.getStatement().setInt("room", (int) form.getItem("room"));
            this.getStatement().setInt("profile", (int) form.getItem("profile"));
        }
        
        else {
            this.setStatement("proc_SelectRoomsAccessPolicy()");
        }
        
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
