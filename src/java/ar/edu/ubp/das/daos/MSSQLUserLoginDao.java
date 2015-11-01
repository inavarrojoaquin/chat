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
public class MSSQLUserLoginDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm form = new DynaActionForm();
        form.setItem("id", result.getInt("id"));
        form.setItem("profile", result.getInt("profile"));
        form.setItem("datetimeOfAccessStart", result.getTimestamp("date_time_of_access_start"));
        form.setItem("datetimeOfAccessEnd", result.getTimestamp("date_time_of_access_end"));
        
        return form;
    }

    @Override
    public void insert(DynaActionForm form) throws Exception {
        this.setStatement("proc_InsertUserLogin(?,?,?,?)");
        CallableStatement statement = this.getStatement();
        
        Date start = (Date) form.getItem("datetimeOfAccessStart");
        Date end = (Date) form.getItem("datetimeOfAccessEnd");
        Timestamp sql_start, sql_end;
        
        sql_start = start == null ? null : new Timestamp(start.getTime());
        sql_end = end == null ? null : new Timestamp(end.getTime());
        
        statement.registerOutParameter("id", INTEGER);        
        statement.setInt("profile", (Integer) form.getItem("profile"));
        statement.setTimestamp("datetimeOfAccessStart",  sql_start);
        statement.setTimestamp("datetimeOfAccessEnd",  sql_end);
        
        this.executeUpdate();
        form.setItem("id", statement.getInt("id"));
    }

    @Override
    public void update(DynaActionForm form) throws Exception {
        this.setStatement("proc_UpdateUserLogin(?,?,?,?)");
        CallableStatement statement = this.getStatement();
        Date start = (Date) form.getItem("datetimeOfAccessStart");
        Date end = (Date) form.getItem("datetimeOfAccessEnd");
        Timestamp sql_start, sql_end;
        
        sql_start = start == null ? null : new Timestamp(start.getTime());
        sql_end = end == null ? null : new Timestamp(end.getTime());
        
        statement.setInt("id", (Integer) form.getItem("id"));
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
            this.setStatement("proc_SelectUserLoginById(?)");
            this.getStatement().setInt("id", (Integer) form.getItem("id"));
        }
        
        else if(selector.equals("byProfile")){
            this.setStatement("proc_SelectUserLoginByProfile(?)");
            this.getStatement().setInt("profile", (Integer) form.getItem("profile"));
        }
        
        else if(selector.equals("byLastLogin")){
            this.setStatement("proc_SelectLastUserLogin(?)");
            this.getStatement().setInt("profile", (Integer) form.getItem("profile"));
        }
        
        else{
            this.setStatement("proc_SelectUsersLogin()");            
        }
        
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
