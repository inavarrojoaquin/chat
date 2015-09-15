/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.ubp.das.mvc.daos;

import java.sql.*;
import java.util.List;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;
import java.util.LinkedList;

/**
 *
 * @author Mariela Pastarini
 */
public abstract class Dao {

    private String driver;
    private String url;
    private String user;
    private String passwd;

    private Connection        connection;
    private CallableStatement statement;

    protected Dao() {}

    @Override
    @SuppressWarnings("FinalizeDeclaration")
    protected void finalize() throws Throwable {
        try {
            if(!this.statement.isClosed()) {
                this.statement.close();
            }
        }
        catch(SQLException ex) {
            throw new Throwable(ex.getMessage());
        }
        finally {
            try {
                if(!this.connection.isClosed()) {
                    this.connection.close();
                }
            }
            catch(SQLException ex) { 
                throw new Throwable(ex.getMessage());
            }
            finally {
                super.finalize();
            }
        }

    }

    protected void setDriver(String driver) {
        this.driver = driver;
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    protected void setUser(String user) {
        this.user = user;
    }

    protected void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    protected void connect() throws Exception {

        try {
            Class.forName(this.driver).newInstance();
            this.connection = DriverManager.getConnection(this.url, this.user, this.passwd);
            this.connection.setAutoCommit(true);
        }
        catch(InstantiationException e1) {
            throw new Exception("Dao 1 - connect: " + e1.getMessage());
        }
        catch(IllegalAccessException e2) {
            throw new Exception("Dao 2 - connect: " + e2.getMessage());
        }
        catch(ClassNotFoundException e3) {
            throw new Exception("Dao 3 - connect: " + e3.getMessage());
        }
        catch(SQLException e4) {
            throw new Exception ("Dao 4 - connect: " + e4.getMessage());
        }

    }

    public void setStatement(String statement) throws Exception {

        try {
            if(this.connection == null) {
                this.connect();
            }

            this.statement = this.connection.prepareCall("{ CALL " + statement + " }");
        }
        catch(SQLException ex) {
            throw new Exception("Dao - setStatement: " + ex.getMessage());
        }

    }

    public CallableStatement getStatement() {
        return this.statement;
    }

    public void executeUpdate() throws Exception {

        try {
            this.connection.setAutoCommit(false);
            this.statement.executeUpdate();
            this.connection.commit();
        }
        catch(SQLException ex) {
            this.connection.rollback();
            throw new Exception("Dao - executeUpdate: " + ex.getMessage());
        }
        finally {
            this.connection.setAutoCommit(true);
        }

    }

    public List<DynaActionForm> execute() throws Exception {

        List<DynaActionForm> list = new LinkedList<DynaActionForm>();

        ResultSet result = this.statement.executeQuery();
        while(result.next()) {
            list.add(make(result));
        }
        return list;

    }

    public abstract DynaActionForm make(ResultSet result) throws Exception;

    public abstract void insert(DynaActionForm form) throws Exception;

    public abstract void update(DynaActionForm form) throws Exception;

    public abstract void delete(DynaActionForm form) throws Exception;

    public abstract List<DynaActionForm> select(DynaActionForm form) throws Exception;

    public abstract boolean valid(DynaActionForm form) throws Exception;

}
