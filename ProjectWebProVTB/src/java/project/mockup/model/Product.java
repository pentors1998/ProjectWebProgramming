/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.mockup.model;

/**
 *
 * @author Admin
 */
public class Product {

    private String productCode;
    private String productBrandName;
    private String productLine;
    private String productType;
    private String productSex;
    private double productSize;
    private double productPrice;

    public Product(String productCode, String productBrandName, String productLine, String productType, String productSex, double productSize, double productPrice) {
        this.productCode = productCode;
        this.productBrandName = productBrandName;
        this.productLine = productLine;
        this.productType = productType;
        this.productSex = productSex;
        this.productSize = productSize;
        this.productPrice = productPrice;
    }

    public Product() {
        
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductSex() {
        return productSex;
    }

    public void setProductSex(String productSex) {
        this.productSex = productSex;
    }

    public double getProductSize() {
        return productSize;
    }

    public void setProductSize(double productSize) {
        this.productSize = productSize;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

   
    
    
}
