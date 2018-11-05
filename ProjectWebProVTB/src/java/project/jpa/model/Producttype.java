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
@Table(name = "PRODUCTTYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producttype.findAll", query = "SELECT p FROM Producttype p")
    , @NamedQuery(name = "Producttype.findByProducttype", query = "SELECT p FROM Producttype p WHERE p.producttype = :producttype")
    , @NamedQuery(name = "Producttype.findByTextdescription", query = "SELECT p FROM Producttype p WHERE p.textdescription = :textdescription")})
public class Producttype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PRODUCTTYPE")
    private String producttype;
    @Size(max = 120)
    @Column(name = "TEXTDESCRIPTION")
    private String textdescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producttype")
    private List<Product> productList;

    public Producttype() {
    }

    public Producttype(String producttype) {
        this.producttype = producttype;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getTextdescription() {
        return textdescription;
    }

    public void setTextdescription(String textdescription) {
        this.textdescription = textdescription;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (producttype != null ? producttype.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producttype)) {
            return false;
        }
        Producttype other = (Producttype) object;
        if ((this.producttype == null && other.producttype != null) || (this.producttype != null && !this.producttype.equals(other.producttype))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project.jpa.model.Producttype[ producttype=" + producttype + " ]";
    }
    
}
