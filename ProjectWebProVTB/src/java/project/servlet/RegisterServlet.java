/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import project.jpa.model.Account;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import project.jpa.model.controller.AccountJpaController;
import project.jpa.model.controller.exceptions.RollbackFailureException;
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
        request.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String email = request.getParameter("email");

        String password = request.getParameter("password");
        password = cryptWithMD5(password);
        password = password.substring(0, 19);

        String tell = request.getParameter("tell");
        String address = request.getParameter("address");
        String debit = request.getParameter("debit");
        String pincode = request.getParameter("pincode");
        HttpSession session = request.getSession(false);
        AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);

        List<Account> accountInDb = accountJpaCtrl.findAccountEntities();

        if (firstName != null && firstName.trim().length() > 0 && lastName != null && lastName.trim().length() > 0
                && email != null && email.trim().length() > 0 && password != null && password.trim().length() > 0
                && tell != null && tell.trim().length() > 0 && debit != null && debit.trim().length() > 0
                && address != null && address.trim().length() > 0 && pincode != null && pincode.trim().length() > 0) {
            for (Account account : accountInDb) {
                if (account.getEmail().equalsIgnoreCase(email)) {
                    request.setAttribute("warn", "Already email.");
                    getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
                }
                Account newAccount = new Account(email, password, firstName, lastName, tell, address, debit, pincode);
                try {
                    accountJpaCtrl.create(newAccount);
                    request.setAttribute("message", "Register Complete.");
                    getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                    return;
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            request.setAttribute("message", "Invalid data.");
            getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    public static String cryptWithMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
        return null;
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
