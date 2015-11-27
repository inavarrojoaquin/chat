package ar.edu.ubp.das.daos;

import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import ar.edu.ubp.das.mvc.daos.MSSQLDao;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import static java.sql.Types.INTEGER;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Febo
 */
public class MSSQLUserAccessDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm form = new DynaActionForm();
        
        form.setItem("id", result.getInt("id"));
        form.setItem("room", result.getInt("room"));
        form.setItem("profile", result.getInt("profile"));
        form.setItem("datetimeOfAccessStart", result.getTimestamp("datetime_of_access_start"));
        form.setItem("datetimeOfAccessEnd", result.getTimestamp("datetime_of_access_end"));
        
        return form;
    }

    @Override
    public void insert(DynaActionForm form) throws Exception {
        this.setStatement("proc_InsertUserAccess(?,?,?,?,?)");
        CallableStatement statement = this.getStatement();
        
        Date start = (Date) form.getItem("datetimeOfAccessStart");
        Date end = (Date) form.getItem("datetimeOfAccessEnd");
        
        Timestamp sql_start, sql_end;
        sql_start = start == null ? null : new Timestamp(start.getTime());
        sql_end = end == null ? null : new Timestamp(end.getTime());
        
        statement.registerOutParameter("id", INTEGER);
        statement.setInt("room", (Integer) form.getItem("room"));
        statement.setInt("profile", (Integer) form.getItem("profile"));
        statement.setTimestamp("datetimeOfAccessStart", sql_start);
        statement.setTimestamp("datetimeOfAccessEnd", sql_end);
        
        this.executeUpdate();
        form.setItem("id", statement.getInt("id"));
    }

    @Override
    public void update(DynaActionForm form) throws Exception {
        this.setStatement("proc_UpdateUserAccess(?,?,?,?,?)");
        CallableStatement statement = this.getStatement();
        
        Date start = (Date) form.getItem("datetimeOfAccessStart");
        Date end = (Date) form.getItem("datetimeOfAccessEnd");
        
        Timestamp sql_start, sql_end;
        sql_start = start == null ? null : new Timestamp(start.getTime());
        sql_end = end == null ? null : new Timestamp(end.getTime());
        
        statement.setInt("id", (int) form.getItem("id"));
        statement.setInt("room", (Integer) form.getItem("room"));
        statement.setInt("profile", (Integer) form.getItem("profile"));
        statement.setTimestamp("datetimeOfAccessStart", sql_start);
        statement.setTimestamp("datetimeOfAccessEnd", sql_end);
        
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
            this.setStatement("proc_SelectUserAccessById(?)");
            this.getStatement().setInt("id", (int) form.getItem("id"));
        }
        
        else if(selector.equals("byRoom")){
            this.setStatement("proc_SelectUserAccessByRoom(?)");
            this.getStatement().setInt("room", (int) form.getItem("room"));
        }
        
        else if(selector.equals("lastByProfile")){
            this.setStatement("proc_SelectLastUserAccessByProfile(?)");
            this.getStatement().setInt("profile", (int) form.getItem("profile"));
        }
        
        else {
            this.setStatement("proc_SelectUsersAccess()");
        }
        
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
