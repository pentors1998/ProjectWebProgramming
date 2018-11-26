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
    , @NamedQuery(name = "Historyorderdetail.findByOrderid", query = "SELECT h FROM Historyorderdetail h WHERE h.orderid = :orderid")
    , @NamedQuery(name = "Historyorderdetail.findByProductquantity", query = "SELECT h FROM Historyorderdetail h WHERE h.productquantity = :productquantity")
    , @NamedQuery(name = "Historyorderdetail.findByProductprice", query = "SELECT h FROM Historyorderdetail h WHERE h.productprice = :productprice")})
public class Historyorderdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERID")
    private Integer orderid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTQUANTITY")
    private int productquantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTPRICE")
    private int productprice;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Historyorder historyorder;
    @JoinColumn(name = "PRODUCTCODE", referencedColumnName = "PRODUCTCODE")
    @ManyToOne(optional = false)
    private Product productcode;

    public Historyorderdetail() {
    }

    public Historyorderdetail(Integer orderid) {
        this.orderid = orderid;
    }

    public Historyorderdetail(Integer orderid, int productquantity, int productprice) {
        this.orderid = orderid;
        this.productquantity = productquantity;
        this.productprice = productprice;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
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

    public Historyorder getHistoryorder() {
        return historyorder;
    }

    public void setHistoryorder(Historyorder historyorder) {
        this.historyorder = historyorder;
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
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historyorderdetail)) {
            return false;
        }
        Historyorderdetail other = (Historyorderdetail) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project.jpa.model.Historyorderdetail[ orderid=" + orderid + " ]";
    }
    
}
