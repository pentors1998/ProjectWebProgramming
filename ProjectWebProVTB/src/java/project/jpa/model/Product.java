/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.jpa.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByProductcode", query = "SELECT p FROM Product p WHERE p.productcode = :productcode")
    , @NamedQuery(name = "Product.findByProductbrandname", query = "SELECT p FROM Product p WHERE lower(p.productbrandname) like :productbrandname " + 
            "OR lower(p.producttype.producttype) like :productbrandname " + "OR lower(p.productsex.productsex) like :productbrandname " + 
            "OR lower(p.productline) like :productbrandname")
    , @NamedQuery(name = "Product.findByProductline", query = "SELECT p FROM Product p WHERE p.productline = :productline")
    , @NamedQuery(name = "Product.findByProductsize", query = "SELECT p FROM Product p WHERE p.productsize = :productsize")
    , @NamedQuery(name = "Product.findByProductprice", query = "SELECT p FROM Product p WHERE p.productprice = :productprice")
    , @NamedQuery(name = "Product.findByQuantityinstock", query = "SELECT p FROM Product p WHERE p.quantityinstock = :quantityinstock")
    , @NamedQuery(name = "Product.findByProductdescription", query = "SELECT p FROM Product p WHERE p.productdescription = :productdescription")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PRODUCTCODE")
    private String productcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PRODUCTBRANDNAME")
    private String productbrandname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PRODUCTLINE")
    private String productline;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTSIZE")
    private int productsize;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTPRICE")
    private int productprice;
    @Column(name = "QUANTITYINSTOCK")
    private Integer quantityinstock;
    @Size(max = 400)
    @Column(name = "PRODUCTDESCRIPTION")
    private String productdescription;
    @JoinColumn(name = "PRODUCTSEX", referencedColumnName = "PRODUCTSEX")
    @ManyToOne(optional = false)
    private Productsex productsex;
    @JoinColumn(name = "PRODUCTTYPE", referencedColumnName = "PRODUCTTYPE")
    @ManyToOne(optional = false)
    private Producttype producttype;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productcode")
    private List<Historyorderdetail> historyorderdetailList;

    public Product() {
    }

    public Product(String productcode) {
        this.productcode = productcode;
    }

    public Product(String productcode, String productbrandname, String productline, int productsize, int productprice) {
        this.productcode = productcode;
        this.productbrandname = productbrandname;
        this.productline = productline;
        this.productsize = productsize;
        this.productprice = productprice;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getProductbrandname() {
        return productbrandname;
    }

    public void setProductbrandname(String productbrandname) {
        this.productbrandname = productbrandname;
    }

    public String getProductline() {
        return productline;
    }

    public void setProductline(String productline) {
        this.productline = productline;
    }

    public int getProductsize() {
        return productsize;
    }

    public void setProductsize(int productsize) {
        this.productsize = productsize;
    }

    public int getProductprice() {
        return productprice;
    }

    public void setProductprice(int productprice) {
        this.productprice = productprice;
    }

    public Integer getQuantityinstock() {
        return quantityinstock;
    }

    public void setQuantityinstock(Integer quantityinstock) {
        this.quantityinstock = quantityinstock;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public Productsex getProductsex() {
        return productsex;
    }

    public void setProductsex(Productsex productsex) {
        this.productsex = productsex;
    }

    public Producttype getProducttype() {
        return producttype;
    }

    public void setProducttype(Producttype producttype) {
        this.producttype = producttype;
    }

    @XmlTransient
    public List<Historyorderdetail> getHistoryorderdetailList() {
        return historyorderdetailList;
    }

    public void setHistoryorderdetailList(List<Historyorderdetail> historyorderdetailList) {
        this.historyorderdetailList = historyorderdetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productcode != null ? productcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productcode == null && other.productcode != null) || (this.productcode != null && !this.productcode.equals(other.productcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project.jpa.model.Product[ productcode=" + productcode + " ]";
    }
    
}
