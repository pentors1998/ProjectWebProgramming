/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import project.jpa.model.Account;
import project.jpa.model.Historyorder;
import project.jpa.model.Historyorderdetail;
import project.jpa.model.Product;
import project.jpa.model.controller.AccountJpaController;
import project.jpa.model.controller.HistoryorderJpaController;
import project.jpa.model.controller.HistoryorderdetailJpaController;
import project.jpa.model.controller.ProductJpaController;
import project.jpa.model.controller.exceptions.PreexistingEntityException;
import project.jpa.model.controller.exceptions.RollbackFailureException;
import project.model.LineItem;
import project.model.ShoppingCart;

/**
 *
 * @author Admin
 */
public class CheckOutServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        Account accountObj = (Account) session.getAttribute("account");
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);
        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
        HistoryorderJpaController historyOrderJpaCtrl = new HistoryorderJpaController(utx, emf);
        HistoryorderdetailJpaController historyOrderDetailJpaCtrl = new HistoryorderdetailJpaController(utx, emf);

        //*--- Start of order ---*
        int orderId = historyOrderJpaCtrl.getHistoryorderCount() + 1;

        Historyorder historyOrder = new Historyorder(orderId, new Date(), "Debit Card", cart.getTotalQuantity(),
                (int) cart.getTotalPrice(), accountObj);

        List<Historyorder> orderList = historyOrderJpaCtrl.findHistoryorderEntities();
        List<Historyorder> orderAccount = new ArrayList<>();

        for (Historyorder order : orderList) {
            if (order.getEmail().getEmail() == accountObj.getEmail()) {
                orderAccount.add(order);
            }
        }

        accountObj.setHistoryorderList(orderAccount);

        try {
            accountJpaCtrl.edit(accountObj);
            historyOrderJpaCtrl.create(historyOrder);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //*--- End of Order ---*

        //*--- Start of OrderDetail ---*
        for (LineItem productLineItems : cart.getLineItems()) {
            int orderDetailId = historyOrderDetailJpaCtrl.getHistoryorderdetailCount() + 1;
            Historyorderdetail orderDetail = new Historyorderdetail();
            List<Historyorderdetail> orderProductDetail = new ArrayList<>();

            orderDetail.setOrderdetailid(orderDetailId);
            orderDetail.setOrderid(historyOrder);
            orderDetail.setProductcode(productLineItems.getProduct());

            int price = (int) productLineItems.getSalePrice() * productLineItems.getQuantity();

            orderDetail.setProductprice(price);
            orderDetail.setProductquantity(productLineItems.getQuantity());

            productLineItems.getProduct().setQuantityinstock(productLineItems.getProduct().getQuantityinstock() - productLineItems.getQuantity());

            orderProductDetail.add(orderDetail);
            historyOrder.setHistoryorderdetailList(orderProductDetail);

            try {
                productJpaCtrl.edit(productLineItems.getProduct());
                historyOrderDetailJpaCtrl.create(orderDetail);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //*--- End of OrderDetail ---*

        for (LineItem productLineItems : cart.getLineItems()) {
            cart.remove(productLineItems.getProduct());
        }

        session.setAttribute("account", accountJpaCtrl.findAccount(accountObj.getEmail()));
        getServletContext().getRequestDispatcher("/Thanks.jsp").forward(request, response);
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
