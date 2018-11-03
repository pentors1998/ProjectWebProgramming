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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "HISTORYORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historyorder.findAll", query = "SELECT h FROM Historyorder h")
    , @NamedQuery(name = "Historyorder.findByProductcode", query = "SELECT h FROM Historyorder h WHERE h.productcode = :productcode")
    , @NamedQuery(name = "Historyorder.findByProductbrandname", query = "SELECT h FROM Historyorder h WHERE h.productbrandname = :productbrandname")
    , @NamedQuery(name = "Historyorder.findByProductline", query = "SELECT h FROM Historyorder h WHERE h.productline = :productline")
    , @NamedQuery(name = "Historyorder.findByProducttype", query = "SELECT h FROM Historyorder h WHERE h.producttype = :producttype")
    , @NamedQuery(name = "Historyorder.findByProductsize", query = "SELECT h FROM Historyorder h WHERE h.productsize = :productsize")
    , @NamedQuery(name = "Historyorder.findByProductprice", query = "SELECT h FROM Historyorder h WHERE h.productprice = :productprice")})
public class Historyorder implements Serializable {

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
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private Account email;
    @JoinColumn(name = "PRODUCTCODE", referencedColumnName = "PRODUCTCODE", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Product product;

    public Historyorder() {
    }

    public Historyorder(String productcode) {
        this.productcode = productcode;
    }

    public Historyorder(String productcode, String productbrandname, String productline, String producttype, int productsize, int productprice) {
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

    public Account getEmail() {
        return email;
    }

    public void setEmail(Account email) {
        this.email = email;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        if (!(object instanceof Historyorder)) {
            return false;
        }
        Historyorder other = (Historyorder) object;
        if ((this.productcode == null && other.productcode != null) || (this.productcode != null && !this.productcode.equals(other.productcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bank.model.Historyorder[ productcode=" + productcode + " ]";
    }
    
}
