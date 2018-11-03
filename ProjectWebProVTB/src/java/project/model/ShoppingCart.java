/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model;

import project.mockup.model.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author INT303
 */
public class ShoppingCart implements Serializable {

    private Map<String, TypeItem> cart;

    public ShoppingCart() {
        cart = new HashMap();
    }

public void add(Product p) {
        TypeItem type = cart.get(p.getProductCode());
        if (type == null) {
            cart.put(p.getProductCode(), new TypeItem(p));
        } else {
            type.setQuantity(type.getQuantity() + 1);
        }
    }

    public void remove(Product p) {
        this.remove(p.getProductCode());
    }

    public void remove(String productCode) {
        cart.remove(productCode);
    }

    public double getTotalPrice() {
        double sum = 0;
        Collection<TypeItem> typeItems = cart.values();
        for (TypeItem typeItem : typeItems) {
            sum += typeItem.getTotalPrice();
        }
        return sum;
    }

    public int getTotalQuantity() {
        int sum = 0;
        Collection<TypeItem> lineItems = cart.values();
        for (TypeItem lineItem : lineItems) {
            sum += lineItem.getQuantity();
        }
        return sum;
    }

    public List<TypeItem> getLineItems() {
        return new ArrayList(cart.values());
    }

}
