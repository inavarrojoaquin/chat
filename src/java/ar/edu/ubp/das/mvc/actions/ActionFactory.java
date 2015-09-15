/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.ubp.das.mvc.actions;

/**
 *
 * @author Mariela Pastarini
 */
public class ActionFactory {

    private ActionFactory() {}

    public static Action getAction(String actionName) throws Exception {

        try {
            return Action.class.cast(Class.forName("ar.edu.ubp.das.actions." + actionName + "Action").newInstance());
        }
        catch(InstantiationException e1) {
            throw new Exception ("ActionFactory 1 - getAction: " + e1.getMessage());
        }
        catch(IllegalAccessException e2) {
            throw new Exception ("ActionFactory 2 - getAction: " + e2.getMessage());
        }
        catch(ClassNotFoundException e3) {
            throw new Exception ("ActionFactory 3 - getAction: " + e3.getMessage());
        }

    }

}
