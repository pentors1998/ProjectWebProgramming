/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import project.model.ShoppingCart;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import project.jpa.model.Product;
import project.jpa.model.controller.ProductJpaController;

/**
 *
 * @author Admin
 */
public class AddToCartServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);
//        String quantityS = request.getParameter("quantity");
//        int quantity = Integer.parseInt(quantityS);

        String url = request.getParameter("url");

        if (url != null) {
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            if (cart == null) {

                cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            }

            String productCode = request.getParameter("productcode");

            if (productCode != null) {
                ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
                Product pd = productJpaCtrl.findProduct(productCode);

                cart.add(pd);

                response.sendRedirect(url);

                return;
            } else {
                getServletContext().getRequestDispatcher("/ProductListServlet?catagories=shop").forward(request, response);
            }
        }

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
        String productCode = request.getParameter("productcode");

        if (productCode != null) {
            Product pd = productJpaCtrl.findProduct(productCode);

            cart.add(pd);

            getServletContext().getRequestDispatcher("/CartServlet").forward(request, response);
            return;
        }

        getServletContext().getRequestDispatcher("/ProductListServlet?catagories=shop").forward(request, response);
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
