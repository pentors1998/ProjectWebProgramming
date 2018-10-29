/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import project.model.Account;
import project.model.jpa.controller.AccountJpaController;
import project.model.jpa.controller.exceptions.RollbackFailureException;
import static project.servlet.LoginServlet.cryptWithMD5;

/**
 *
 * @author Admin
 */
public class RegisterServlet extends HttpServlet {

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
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String tell = request.getParameter("tell");
        String address = request.getParameter("address");
        String debit = request.getParameter("debit");
        HttpSession session = request.getSession(false);
        AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);
        Account accountInDb = accountJpaCtrl.findAccount(email);
        if (firstName != null && firstName.trim().length() > 0 && lastName != null && lastName.trim().length() > 0
                && email != null && email.trim().length() > 0 && password != null && password.trim().length() > 0
                && tell != null && tell.trim().length() > 0 && debit != null && debit.trim().length() > 0
                && address != null && address.trim().length() > 0 && !accountInDb.getEmail().equalsIgnoreCase(email)) {
            password = cryptWithMD5(password);
            Account newAccount = new Account(email, password, firstName, lastName, tell, address, debit);
            try {
                accountJpaCtrl.create(newAccount);
                request.setAttribute("message", "Register Complete.");
                getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
                return;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.setAttribute("message", "Invalid data.");
            getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
        }
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
