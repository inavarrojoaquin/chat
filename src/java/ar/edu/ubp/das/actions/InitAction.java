/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.ubp.das.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ar.edu.ubp.das.mvc.actions.Action;

/**
 *
 * @author Mariela Pastarini
 */
public class InitAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Código de arranque de la aplicación
        this.gotoPage("/template/login.jsp", request, response);
        
    }
    
}
