/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model;

import java.io.Serializable;
import project.jpa.model.Product;

/**
 *
 * @author INT303
 */
public class LineItem implements Serializable {

    private Product product;
    private double salePrice;
    private int quantity;

    public LineItem() {

    }

    public LineItem(Product product) {
        this(product, 1);
    }

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.salePrice = product.getProductprice();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return quantity * salePrice;
    }
}
