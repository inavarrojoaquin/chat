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
public class MSSQLMessageDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm form = new DynaActionForm();
        
        form.setItem("id", result.getInt("id"));
        form.setItem("room", result.getInt("room"));
        form.setItem("owner", result.getInt("owner"));
        form.setItem("datetimeOfCreation", result.getTimestamp("datetime_of_creation"));
        form.setItem("body", result.getString("body"));
        form.setItem("state", result.getString("state"));
        
        return form;
    }

    @Override
    public void insert(DynaActionForm form) throws Exception {
        this.setStatement("proc_InsertMessage(?,?,?,?,?,?)");
        CallableStatement statement = this.getStatement();
        Date start = (Date) form.getItem("datetimeOfCreation");
        Timestamp sql_start = start == null ? null : new Timestamp( start.getTime() );
        
        statement.registerOutParameter("id",INTEGER);
        statement.setInt("room", (int) form.getItem("room"));
        statement.setInt("owner", (int) form.getItem("owner"));
        statement.setTimestamp("datetimeOfCreation", sql_start);
        statement.setString("body", (String) form.getItem("body"));
        statement.setString("state", (String) form.getItem("state"));
        
        this.executeUpdate();
        form.setItem("id", statement.getInt("id"));
    }

    @Override
    public void update(DynaActionForm form) throws Exception {
        this.setStatement("proc_UpdateMessage(?,?,?,?,?,?)");
        CallableStatement statement = this.getStatement();
        Date start = (Date) form.getItem("datetimeOfCreation");
        Timestamp sql_start = start == null ? null : new Timestamp( start.getTime() );
        
        statement.setInt("id", (int) form.getItem("id"));
        statement.setInt("room", (int) form.getItem("room"));
        statement.setInt("owner", (int) form.getItem("owner"));
        statement.setTimestamp("datetimeOfCreation", sql_start);
        statement.setString("body", (String) form.getItem("body"));
        statement.setString("state", (String) form.getItem("state"));
        
        this.executeUpdate();
    }

    @Override
    public void delete(DynaActionForm form) throws Exception {
        this.setStatement("proc_DeleteMessage(?)");
        CallableStatement statement = this.getStatement();
        statement.setInt("id", (int) form.getItem("id"));
        
        this.executeUpdate();
    }

    @Override
    public List<DynaActionForm> select(DynaActionForm form) throws Exception {
        String selector = (String) form.getItem("selector");
        
        if(selector.equals("byId")){
            this.setStatement("proc_SelectMessageById(?)");
            this.getStatement().setInt("id", (int) form.getItem("id"));
        }
        
        else if(selector.equals("byRoom")){
            this.setStatement("proc_SelectMessageByRoom(?)");
            this.getStatement().setInt("room", (int) form.getItem("room"));
        }
        
        else if(selector.equals("byRoomAndProfile")){
            this.setStatement("proc_SelectMessageByRoomAndProfile(?, ?)");
            this.getStatement().setInt("room", (int) form.getItem("room"));
            this.getStatement().setInt("profile", (int) form.getItem("profile"));
        }
                        
        else if(selector.equals("byOwner")){
            this.setStatement("proc_SelectMessageByOwner(?)");
            this.getStatement().setInt("owner", (int) form.getItem("owner"));
        }
        
        else {
            this.setStatement("proc_SelectMessages()");
        }
        
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
