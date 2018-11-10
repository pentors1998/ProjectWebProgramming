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
@Table(name = "PRODUCTSEX")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productsex.findAll", query = "SELECT p FROM Productsex p")
    , @NamedQuery(name = "Productsex.findByProductsex", query = "SELECT p FROM Productsex p WHERE p.productsex = :productsex")})
public class Productsex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "PRODUCTSEX")
    private String productsex;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productsex")
    private List<Product> productList;

    public Productsex() {
    }

    public Productsex(String productsex) {
        this.productsex = productsex;
    }

    public String getProductsex() {
        return productsex;
    }

    public void setProductsex(String productsex) {
        this.productsex = productsex;
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
        hash += (productsex != null ? productsex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productsex)) {
            return false;
        }
        Productsex other = (Productsex) object;
        if ((this.productsex == null && other.productsex != null) || (this.productsex != null && !this.productsex.equals(other.productsex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project.jpa.model.Productsex[ productsex=" + productsex + " ]";
    }
    
}
