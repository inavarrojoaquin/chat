package ar.edu.ubp.das.daos;

import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import ar.edu.ubp.das.mvc.daos.MSSQLDao;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Febo
 */
public class MSSQLMessageComplexDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm form = new DynaActionForm();
        
        form.setItem("id", result.getInt("id"));
        form.setItem("room", result.getInt("room"));
        form.setItem("owner", result.getInt("owner"));
        form.setItem("datetimeOfCreation", result.getTimestamp("datetime_of_creation"));
        form.setItem("body", result.getString("body"));
        form.setItem("state", result.getString("state"));
        form.setItem("ownerName", result.getString("login"));
        
        return form;
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
        
        if(selector.equals("byRoomAndProfile")){
            this.setStatement("proc_SelectMessageByRoomAndProfile(?, ?)");
            this.getStatement().setInt("room", (int) form.getItem("room"));
            this.getStatement().setInt("profile", (int) form.getItem("profile"));
        }
        
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
