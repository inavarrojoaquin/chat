package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.mvc.actions.Action;
import ar.edu.ubp.das.mvc.daos.Dao;
import ar.edu.ubp.das.mvc.daos.DaoFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewProfileAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Dao profile = DaoFactory.getDao("Profile");
        profile.insert(this.getForm());
        //response.getWriter().println("New Profile");
    }
    
}
