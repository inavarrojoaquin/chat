package ar.edu.ubp.das.daos;

import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import ar.edu.ubp.das.mvc.daos.MSSQLDao;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import static java.sql.Types.INTEGER;
import java.util.List;

public class MSSQLProfileDao extends MSSQLDao{

    @Override
    public DynaActionForm make(ResultSet result) throws Exception {
        DynaActionForm profile = new DynaActionForm();
        profile.setItem("id", result.getInt("id"));
        profile.setItem("login", result.getString("login"));
        profile.setItem("password", result.getString("password"));
        profile.setItem("type", result.getString("type"));
        return profile;
    }

    @Override
    public void insert(DynaActionForm form) throws Exception {
        this.setStatement("proc_InsertProfile(?,?,?)");
        CallableStatement statement = this.getStatement();
        
        statement.registerOutParameter("id", INTEGER);
        statement.setString("login", String.valueOf(form.getItem("login")));
        statement.setString("password", String.valueOf(form.getItem("password")));
        
        this.executeUpdate();
        form.setItem("id", statement.getInt("id"));
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
        
        if(selector.equals("byId")){
            this.setStatement("proc_SelectProfileById(?)");
            this.getStatement().setInt("id", (Integer) form.getItem("id"));
        }
        
        else if(selector.equals("byLogin")){
            this.setStatement("proc_SelectProfileByLogin(?)");
            this.getStatement().setString("login", (String) form.getItem("login"));
        }
        else if(selector.equals("byLoginAndPassword")){
            this.setStatement("proc_SelectProfileByLoginAndPassword(?, ?)");
            this.getStatement().setString("login", (String) form.getItem("login"));
            this.getStatement().setString("password", (String) form.getItem("password"));
        }
        
        else if(selector.equals("byRoom")){
            this.setStatement("proc_SelectParticipantsForRoom(?)");
            this.getStatement().setInt("room", (Integer) form.getItem("room"));
        }
        
        else if(selector.equals("byUsersActives")){
            this.setStatement("proc_SelectActivesUsersLogin()");
        }
        
        else if(selector.equals("bySearchUsersActives")){
            this.setStatement("proc_SearchUsersLogin(?)");
            this.getStatement().setString("string_search", (String) form.getItem("string_search"));
        }
                
        else if(selector.equals("rejectedInvitationsByRoom")){
            this.setStatement("proc_SelectRejectedInvitationsByRoom(?)");
            this.getStatement().setInt("room", (Integer) form.getItem("room"));
        }
        
        else{
            this.setStatement("proc_SelectProfiles()");
        }
        return this.execute();
    }

    @Override
    public boolean valid(DynaActionForm form) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
