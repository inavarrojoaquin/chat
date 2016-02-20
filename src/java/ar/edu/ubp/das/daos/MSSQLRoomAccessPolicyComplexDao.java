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
public class MSSQLRoomAccessPolicyComplexDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm form = new DynaActionForm();
        
        form.setItem("id", result.getInt("id"));
        form.setItem("room", result.getInt("room"));
        form.setItem("profile", result.getInt("profile"));
        form.setItem("policy", result.getString("policy"));
        form.setItem("userName", result.getString("login"));
        form.setItem("roomName", result.getString("name"));
        
        return form;
    }

    @Override
    public void insert(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(DynaActionForm form) throws Exception {
        this.setStatement("proc_UpdateRoomsAccessPolicyByPolicyId(?)");
        CallableStatement statement = this.getStatement();
        statement.setInt("policy", (int) form.getItem("policyId"));
        
        this.executeUpdate();
    }

    @Override
    public void delete(DynaActionForm form) throws Exception {
        this.setStatement("proc_DeleteRoomsAccessPolicyByPolicyId(?)");
        CallableStatement statement = this.getStatement();
        statement.setInt("policy", (int) form.getItem("policyId"));
        
        this.executeUpdate();
    }

    @Override
    public List<DynaActionForm> select(DynaActionForm form) throws Exception {
        String selector = (String) form.getItem("selector");
        
        if(selector.equals("findAll")){
            this.setStatement("proc_SelectRoomsAccessPolicy()");
        }
        
        else if (selector.equals("byProfile")){
            this.setStatement("proc_SelectRoomAccessPolicyByProfile(?)");
            this.getStatement().setInt("profile", (int) form.getItem("profileId"));
        }
        
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
