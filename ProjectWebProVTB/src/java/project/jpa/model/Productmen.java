/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "PRODUCTMEN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productmen.findAll", query = "SELECT p FROM Productmen p")
    , @NamedQuery(name = "Productmen.findByProductcode", query = "SELECT p FROM Productmen p WHERE p.productcode = :productcode")
    , @NamedQuery(name = "Productmen.findByProductbrandname", query = "SELECT p FROM Productmen p WHERE p.productbrandname = :productbrandname")
    , @NamedQuery(name = "Productmen.findByProductline", query = "SELECT p FROM Productmen p WHERE p.productline = :productline")
    , @NamedQuery(name = "Productmen.findByProducttype", query = "SELECT p FROM Productmen p WHERE p.producttype = :producttype")
    , @NamedQuery(name = "Productmen.findByProductsize", query = "SELECT p FROM Productmen p WHERE p.productsize = :productsize")
    , @NamedQuery(name = "Productmen.findByProductprice", query = "SELECT p FROM Productmen p WHERE p.productprice = :productprice")
    , @NamedQuery(name = "Productmen.findByProductdescription", query = "SELECT p FROM Productmen p WHERE p.productdescription = :productdescription")
    , @NamedQuery(name = "Productmen.findByQuantityinstock", query = "SELECT p FROM Productmen p WHERE p.quantityinstock = :quantityinstock")})
public class Productmen implements Serializable {

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
    @Size(min = 1, max = 50)
    @Column(name = "PRODUCTTYPE")
    private String producttype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTSIZE")
    private int productsize;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTPRICE")
    private int productprice;
    @Size(max = 120)
    @Column(name = "PRODUCTDESCRIPTION")
    private String productdescription;
    @Column(name = "QUANTITYINSTOCK")
    private Integer quantityinstock;

    public Productmen() {
    }

    public Productmen(String productcode) {
        this.productcode = productcode;
    }

    public Productmen(String productcode, String productbrandname, String productline, String producttype, int productsize, int productprice) {
        this.productcode = productcode;
        this.productbrandname = productbrandname;
        this.productline = productline;
        this.producttype = producttype;
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

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
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

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public Integer getQuantityinstock() {
        return quantityinstock;
    }

    public void setQuantityinstock(Integer quantityinstock) {
        this.quantityinstock = quantityinstock;
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
        if (!(object instanceof Productmen)) {
            return false;
        }
        Productmen other = (Productmen) object;
        if ((this.productcode == null && other.productcode != null) || (this.productcode != null && !this.productcode.equals(other.productcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project.jpa.model.Productmen[ productcode=" + productcode + " ]";
    }
    
}
