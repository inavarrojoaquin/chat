/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.ubp.das.mvc.actions;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mariela Pastarini
 */
public abstract class Action {

    private ServletContext servletContext;
    private DynaActionForm form;

    protected Action() { }

    protected void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
    protected void setForm(DynaActionForm form) {
        this.form = form;
    }
    
    protected DynaActionForm getForm() {
        return this.form;
    }

    protected void forward(String actionName, HttpServletRequest request, HttpServletResponse response) {

        try {
            Action action = ActionFactory.getAction(actionName);
                   action.setServletContext(this.servletContext);
                   action.setForm(this.form);
                   action.execute(request, response);
        }
        catch(NullPointerException ex1) {
            ActionController.printError(request, response, ex1);
        }
        catch(Exception ex2) {
            ActionController.printError(request, response, ex2);
        }

    }

    protected void gotoPage(String address, HttpServletRequest request, HttpServletResponse response) {

        try {
            RequestDispatcher dispatcher = this.servletContext.getRequestDispatcher(address);
            request.setAttribute("form", this.form.getItems());
            dispatcher.forward(request, response);
        }
        catch(ServletException ex1) {
            ActionController.printError(request, response, ex1);
        }
        catch (IOException ex2) {
            ActionController.printError(request, response, ex2);
        }

    }

    public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
