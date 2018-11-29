/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import project.jpa.model.Account;
import project.jpa.model.controller.AccountJpaController;
import project.jpa.model.controller.exceptions.NonexistentEntityException;
import project.jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Admin
 */
public class EditMyAccountServlet extends HttpServlet {

    @PersistenceUnit(unitName = "ProjectWebProVTBPU")
    EntityManagerFactory emf;
    
    @Resource
    UserTransaction utx;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String tell = request.getParameter("tell");
        String address = request.getParameter("address");
        String debit = request.getParameter("debit");
        String pincode = request.getParameter("pincode");
        
        HttpSession session = request.getSession(false);
        Account accountObj = (Account) session.getAttribute("account");
        AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);
        
        accountObj.setFirstname(firstName);
        accountObj.setLastname(lastName);
        accountObj.setTell(tell);
        accountObj.setAddress(address);
        accountObj.setDebitcard(debit);
        accountObj.setPincode(pincode);
        
        try {
        accountJpaCtrl.edit(accountObj);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EditMyAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(EditMyAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EditMyAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("notice", "Updated profile.");
        getServletContext().getRequestDispatcher("/MyAccountPageServlet").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
