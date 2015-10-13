package ar.edu.ubp.das.actions;

import ar.edu.ubp.das.mvc.actions.Action;
import ar.edu.ubp.das.mvc.daos.Dao;
import ar.edu.ubp.das.mvc.daos.DaoFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileListAction extends Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Dao dao = DaoFactory.getDao("Profile");
        this.getForm().setItem("profileList", dao.select(this.getForm()));
        this.gotoPage("/list.jsp", request, response);                  
    }
    
}
