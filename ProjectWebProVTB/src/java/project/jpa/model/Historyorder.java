/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.jpa.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "HISTORYORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historyorder.findAll", query = "SELECT h FROM Historyorder h")
    , @NamedQuery(name = "Historyorder.findByOrderid", query = "SELECT h FROM Historyorder h WHERE h.orderid = :orderid")
    , @NamedQuery(name = "Historyorder.findByTimedate", query = "SELECT h FROM Historyorder h WHERE h.timedate = :timedate")
    , @NamedQuery(name = "Historyorder.findByMethod", query = "SELECT h FROM Historyorder h WHERE h.method = :method")
    , @NamedQuery(name = "Historyorder.findByAmount", query = "SELECT h FROM Historyorder h WHERE h.amount = :amount")
    , @NamedQuery(name = "Historyorder.findByPrice", query = "SELECT h FROM Historyorder h WHERE h.price = :price")})
public class Historyorder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERID")
    private Integer orderid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIMEDATE")
    @Temporal(TemporalType.DATE)
    private Date timedate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "METHOD")
    private String method;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private int amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private int price;
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private Account email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderid")
    private List<Historyorderdetail> historyorderdetailList;

    public Historyorder() {
    }

    public Historyorder(Integer orderid) {
        this.orderid = orderid;
    }

    public Historyorder(Integer orderid, Date timedate, String method, int amount, int price, Account email) {
        this.orderid = orderid;
        this.timedate = timedate;
        this.method = method;
        this.amount = amount;
        this.price = price;
        this.email = email;
    }

    
    
    public Historyorder(Integer orderid, Date timedate, String method, int amount, int price) {
        this.orderid = orderid;
        this.timedate = timedate;
        this.method = method;
        this.amount = amount;
        this.price = price;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Date getTimedate() {
        return timedate;
    }

    public void setTimedate(Date timedate) {
        this.timedate = timedate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Account getEmail() {
        return email;
    }

    public void setEmail(Account email) {
        this.email = email;
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
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historyorder)) {
            return false;
        }
        Historyorder other = (Historyorder) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project.jpa.model.Historyorder[ orderid=" + orderid + " ]";
    }
    
}
