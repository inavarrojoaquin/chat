/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.ubp.das.mvc.daos;

import java.sql.ResultSet;
import java.util.List;
import ar.edu.ubp.das.mvc.actions.DynaActionForm;

/**
 *
 * @author Mariela Pastarini
 */
public abstract class MSSQLDao extends Dao {

    protected MSSQLDao() {
        this.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.setUrl("jdbc:sqlserver://ocrux;databaseName=das");
        this.setUser("sa");
        this.setPasswd("pyxis");
    }

    @Override
    public abstract DynaActionForm make(ResultSet result) throws Exception;

    @Override
    public abstract void insert(DynaActionForm form) throws Exception;

    @Override
    public abstract void update(DynaActionForm form) throws Exception;

    @Override
    public abstract void delete(DynaActionForm form) throws Exception;

    @Override
    public abstract List<DynaActionForm> select(DynaActionForm form) throws Exception;

    @Override
    public abstract boolean valid(DynaActionForm form) throws Exception;

}
