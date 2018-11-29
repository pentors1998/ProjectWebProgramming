/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import project.jpa.model.Product;

/**
 *
 * @author INT303
 */
public class ShoppingCart implements Serializable {

    private Map<String, LineItem> cart;

    public ShoppingCart() {
        cart = new HashMap();
    }

    public void add(Product p) {
        LineItem type = cart.get(p.getProductcode());
        if (type == null) {
            cart.put(p.getProductcode(), new LineItem(p));
        } else {
            if (type.getQuantity() >= p.getQuantityinstock()) {
                return;
            }
            type.setQuantity(type.getQuantity() + 1);
        }
    }

    public void drop(Product p) {
        LineItem type = cart.get(p.getProductcode());
        if (type == null) {
            cart.put(p.getProductcode(), new LineItem(p));
        } else {
            if (type.getQuantity() > 1) {
                type.setQuantity(type.getQuantity() - 1);
                return;
            } else
                return;
        }
    }

    public void remove(Product p) {
        this.remove(p.getProductcode());
    }

    public void remove(String productCode) {
        cart.remove(productCode);
    }

    public double getTotalPrice() {
        double sum = 0;
        Collection<LineItem> typeItems = cart.values();
        for (LineItem typeItem : typeItems) {
            sum += typeItem.getTotalPrice();
        }
        return sum;
    }

    public int getTotalQuantity() {
        int sum = 0;
        Collection<LineItem> lineItems = cart.values();
        for (LineItem lineItem : lineItems) {
            sum += lineItem.getQuantity();
        }
        return sum;
    }

    public List<LineItem> getLineItems() {
        return new ArrayList(cart.values());
    }

}
