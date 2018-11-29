/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
public class ProductListPriceServlet extends HttpServlet {

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
        //Between Price
        String minPriceS = request.getParameter("minPrice");
        String maxPriceS = request.getParameter("maxPrice");

        HttpSession session = request.getSession(false);
        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);

        if (minPriceS != null && maxPriceS != null) {
            double minPrice = Double.parseDouble(minPriceS);
            double maxPrice = Double.parseDouble(maxPriceS);

            List<Product> products = productJpaCtrl.findProductEntities();
            List<Product> productAdd = new ArrayList<>();

            if (minPrice <= maxPrice) {
                for (Product productSet : products) {
                    if (minPrice <= productSet.getProductprice() && productSet.getProductprice() <= maxPrice) {
                        productAdd.add(productSet);
                    }
                }
            } else {
                request.setAttribute("priceWarn", "'Min' value must be less than 'Max' value");
                getServletContext().getRequestDispatcher("/shop.jsp").forward(request, response);
                return;
            }
            request.setAttribute("topic", "$" + minPrice + " - " + "$" + maxPrice);
            session.setAttribute("products", productAdd);
            getServletContext().getRequestDispatcher("/shop.jsp").forward(request, response);
            return;
        }
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
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
