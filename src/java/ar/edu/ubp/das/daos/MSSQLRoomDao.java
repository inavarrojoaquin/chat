package ar.edu.ubp.das.daos;

import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import ar.edu.ubp.das.mvc.daos.DaoFactory;
import ar.edu.ubp.das.mvc.daos.MSSQLDao;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import static java.sql.Types.INTEGER;
import java.util.List;

/**
 *
 * @author Febo
 */
public class MSSQLRoomDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm form = new DynaActionForm();
        
        form.setItem("id", result.getInt("id"));
        form.setItem("name", result.getString("name"));
        form.setItem("type", result.getString("type"));
        form.setItem("owner", result.getInt("owner"));
        
        return form;
    }

    @Override
    public void insert(DynaActionForm form) throws Exception {
        this.setStatement("proc_InsertRoom(?,?,?,?)");
        CallableStatement statement = getStatement();
        
        statement.registerOutParameter("id", INTEGER);
        statement.setString("name", (String) form.getItem("name"));
        statement.setString("type", (String) form.getItem("type"));
        statement.setObject("owner", (Integer) form.getItem("owner"));
        
        this.executeUpdate();
        form.setItem("id", statement.getInt("id"));
    }

    @Override
    public void update(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(DynaActionForm form) throws Exception {
        this.setStatement("proc_DeleteRoom(?)");
        CallableStatement statement = this.getStatement();
        statement.setInt("id", (int) form.getItem("id"));
        
        this.executeUpdate();
    }

    @Override
    public List<DynaActionForm> select(DynaActionForm form) throws Exception {
        String selector = (String) form.getItem("selector");
        
        if(selector.equals("byId")){
            this.setStatement("proc_SelectRoomById(?)");
            this.getStatement().setInt("id", (Integer) form.getItem("id"));
        }
        
        else if(selector.equals("byName")){
            this.setStatement("proc_SelectRoomByName(?)");
            this.getStatement().setString("name", (String) form.getItem("name"));
        }
        else if(selector.equals("byOwner")){
            this.setStatement("proc_SelectRoomByOwner(?)");
            this.getStatement().setInt("owner", (Integer) form.getItem("owner"));
        }
        else if(selector.equals("byProfile")){
            this.setStatement("proc_SelectParticipantRoomsByProfile(?)");
            this.getStatement().setInt("profile", (Integer) form.getItem("profile"));
        }
        else {
            this.setStatement("proc_SelectRooms()");
        }
        
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
