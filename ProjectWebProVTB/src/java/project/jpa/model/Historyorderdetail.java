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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "HISTORYORDERDETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historyorderdetail.findAll", query = "SELECT h FROM Historyorderdetail h")
    , @NamedQuery(name = "Historyorderdetail.findByOrderdetailid", query = "SELECT h FROM Historyorderdetail h WHERE h.orderdetailid = :orderdetailid")
    , @NamedQuery(name = "Historyorderdetail.findByProductquantity", query = "SELECT h FROM Historyorderdetail h WHERE h.productquantity = :productquantity")
    , @NamedQuery(name = "Historyorderdetail.findByProductprice", query = "SELECT h FROM Historyorderdetail h WHERE h.productprice = :productprice")})
public class Historyorderdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERDETAILID")
    private Integer orderdetailid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTQUANTITY")
    private int productquantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTPRICE")
    private int productprice;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @ManyToOne(optional = false)
    private Historyorder orderid;
    @JoinColumn(name = "PRODUCTCODE", referencedColumnName = "PRODUCTCODE")
    @ManyToOne(optional = false)
    private Product productcode;

    public Historyorderdetail() {
    }

    public Historyorderdetail(Integer orderdetailid) {
        this.orderdetailid = orderdetailid;
    }

    public Historyorderdetail(Integer orderdetailid, int productquantity, int productprice, Historyorder orderid, Product productcode) {
        this.orderdetailid = orderdetailid;
        this.productquantity = productquantity;
        this.productprice = productprice;
        this.orderid = orderid;
        this.productcode = productcode;
    }

    
    
    public Historyorderdetail(Integer orderdetailid, int productquantity, int productprice) {
        this.orderdetailid = orderdetailid;
        this.productquantity = productquantity;
        this.productprice = productprice;
    }

    public Integer getOrderdetailid() {
        return orderdetailid;
    }

    public void setOrderdetailid(Integer orderdetailid) {
        this.orderdetailid = orderdetailid;
    }

    public int getProductquantity() {
        return productquantity;
    }

    public void setProductquantity(int productquantity) {
        this.productquantity = productquantity;
    }

    public int getProductprice() {
        return productprice;
    }

    public void setProductprice(int productprice) {
        this.productprice = productprice;
    }

    public Historyorder getOrderid() {
        return orderid;
    }

    public void setOrderid(Historyorder orderid) {
        this.orderid = orderid;
    }

    public Product getProductcode() {
        return productcode;
    }

    public void setProductcode(Product productcode) {
        this.productcode = productcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderdetailid != null ? orderdetailid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historyorderdetail)) {
            return false;
        }
        Historyorderdetail other = (Historyorderdetail) object;
        if ((this.orderdetailid == null && other.orderdetailid != null) || (this.orderdetailid != null && !this.orderdetailid.equals(other.orderdetailid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project.jpa.model.Historyorderdetail[ orderdetailid=" + orderdetailid + " ]";
    }
    
}
