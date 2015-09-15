/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.ubp.das.mvc.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Mariela Pastarini
 */
@WebServlet(name = "ActionController", urlPatterns = {"/index.jsp"})
public class ActionController extends HttpServlet {

    /** 
     * Print internal error.
     * @param request servlet request
     * @param response servlet response
     * @param exception exception
     * @throws IOException if an I/O error occurs
     */
    protected static void printError(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        
        response.setStatus(400);
        response.setContentType("text/html;charset=ISO-8859-15");
        try {
            PrintWriter out = response.getWriter();
            if(request.getHeader("x-requested-with") == null) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");  
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Reporte de error</h1>");
                out.println("<p>" + exception.getMessage() + "</p>");
                out.println("</body>");
                out.println("</html>");
            }    
            else {
                out.println("<p>" + exception.getMessage() + "</p>");
            }
            out.close();    
        } 
        catch(IOException ex) {
            System.out.print(ex.getMessage());
        } 
        
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=ISO-8859-15");
        try {
            String key, actionName = request.getParameter("action");            
            
            DynaActionForm      form = new DynaActionForm();            
            Enumeration<String> keys = request.getParameterNames();
            while(keys.hasMoreElements()) {
                key = keys.nextElement().toString();
                if(request.getParameterValues(key).length > 1) {
                    form.setItem(key, request.getParameterValues(key));
                }
                else {
                    form.setItem(key, request.getParameter(key));
                }    
            }

            Action action = ActionFactory.getAction(actionName == null ? "Init" : actionName);
                   action.setServletContext(getServletContext());
                   action.setForm(form);
                   action.execute(request, response);
        } 
        catch (Exception ex) {
            ActionController.printError(request, response, ex);
        }  
    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
