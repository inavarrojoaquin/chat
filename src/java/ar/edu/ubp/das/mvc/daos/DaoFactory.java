/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.ubp.das.mvc.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Mariela Pastarini
 */
public class DaoFactory {

    private static Properties prop     = new Properties();
    private static boolean    loadProp = false;

    private DaoFactory() {}

    public static Dao getDao(String daoName) throws Exception {

        try {
            return Dao.class.cast(Class.forName(DaoFactory.getDaoClassName(daoName)).newInstance());
        }
        catch(InstantiationException e1) {
            throw new Exception("DaoFactory 1 - getDao: " + e1.getMessage());
        }
        catch(IllegalAccessException e2) {
            throw new Exception("DaoFactory 2 - getDao: " + e2.getMessage());
        }
        catch(ClassNotFoundException e3) {
            throw new Exception("DaoFactory 3 - getDao: " + e3.getMessage());
        }

    }

    private static String getDaoClassName(String daoName) throws Exception {

        try {
            if(!DaoFactory.loadProp) {
                String          path = new File(new File(DaoFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent()).getParent();
                FileInputStream file = new FileInputStream(path + "\\properties\\DaoFactory.properties");
                DaoFactory.prop.load(file);
                file.close();
                DaoFactory.loadProp = true;
            }
            return "ar.edu.ubp.das.daos." + DaoFactory.prop.getProperty("CurrentDaoFactory") + daoName + "Dao";
        }
        catch(FileNotFoundException e1) {
            throw new Exception("DaoFactory 1 - getDaoClassName: " + e1.getMessage());
        }
        catch(IOException e2) {
            throw new Exception("DaoFactory 2 - getDaoClassName: " + e2.getMessage());
        }

    }

}
