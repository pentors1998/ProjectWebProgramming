/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.jpa.model.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import project.jpa.model.Historyorder;
import project.jpa.model.Historyorderdetail;
import project.jpa.model.Product;
import project.jpa.model.controller.exceptions.NonexistentEntityException;
import project.jpa.model.controller.exceptions.PreexistingEntityException;
import project.jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Admin
 */
public class HistoryorderdetailJpaController implements Serializable {

    public HistoryorderdetailJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historyorderdetail historyorderdetail) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historyorder orderid = historyorderdetail.getOrderid();
            if (orderid != null) {
                orderid = em.getReference(orderid.getClass(), orderid.getOrderid());
                historyorderdetail.setOrderid(orderid);
            }
            Product productcode = historyorderdetail.getProductcode();
            if (productcode != null) {
                productcode = em.getReference(productcode.getClass(), productcode.getProductcode());
                historyorderdetail.setProductcode(productcode);
            }
            em.persist(historyorderdetail);
            if (orderid != null) {
                orderid.getHistoryorderdetailList().add(historyorderdetail);
                orderid = em.merge(orderid);
            }
            if (productcode != null) {
                productcode.getHistoryorderdetailList().add(historyorderdetail);
                productcode = em.merge(productcode);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistoryorderdetail(historyorderdetail.getOrderdetailid()) != null) {
                throw new PreexistingEntityException("Historyorderdetail " + historyorderdetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historyorderdetail historyorderdetail) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historyorderdetail persistentHistoryorderdetail = em.find(Historyorderdetail.class, historyorderdetail.getOrderdetailid());
            Historyorder orderidOld = persistentHistoryorderdetail.getOrderid();
            Historyorder orderidNew = historyorderdetail.getOrderid();
            Product productcodeOld = persistentHistoryorderdetail.getProductcode();
            Product productcodeNew = historyorderdetail.getProductcode();
            if (orderidNew != null) {
                orderidNew = em.getReference(orderidNew.getClass(), orderidNew.getOrderid());
                historyorderdetail.setOrderid(orderidNew);
            }
            if (productcodeNew != null) {
                productcodeNew = em.getReference(productcodeNew.getClass(), productcodeNew.getProductcode());
                historyorderdetail.setProductcode(productcodeNew);
            }
            historyorderdetail = em.merge(historyorderdetail);
            if (orderidOld != null && !orderidOld.equals(orderidNew)) {
                orderidOld.getHistoryorderdetailList().remove(historyorderdetail);
                orderidOld = em.merge(orderidOld);
            }
            if (orderidNew != null && !orderidNew.equals(orderidOld)) {
                orderidNew.getHistoryorderdetailList().add(historyorderdetail);
                orderidNew = em.merge(orderidNew);
            }
            if (productcodeOld != null && !productcodeOld.equals(productcodeNew)) {
                productcodeOld.getHistoryorderdetailList().remove(historyorderdetail);
                productcodeOld = em.merge(productcodeOld);
            }
            if (productcodeNew != null && !productcodeNew.equals(productcodeOld)) {
                productcodeNew.getHistoryorderdetailList().add(historyorderdetail);
                productcodeNew = em.merge(productcodeNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historyorderdetail.getOrderdetailid();
                if (findHistoryorderdetail(id) == null) {
                    throw new NonexistentEntityException("The historyorderdetail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historyorderdetail historyorderdetail;
            try {
                historyorderdetail = em.getReference(Historyorderdetail.class, id);
                historyorderdetail.getOrderdetailid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historyorderdetail with id " + id + " no longer exists.", enfe);
            }
            Historyorder orderid = historyorderdetail.getOrderid();
            if (orderid != null) {
                orderid.getHistoryorderdetailList().remove(historyorderdetail);
                orderid = em.merge(orderid);
            }
            Product productcode = historyorderdetail.getProductcode();
            if (productcode != null) {
                productcode.getHistoryorderdetailList().remove(historyorderdetail);
                productcode = em.merge(productcode);
            }
            em.remove(historyorderdetail);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historyorderdetail> findHistoryorderdetailEntities() {
        return findHistoryorderdetailEntities(true, -1, -1);
    }

    public List<Historyorderdetail> findHistoryorderdetailEntities(int maxResults, int firstResult) {
        return findHistoryorderdetailEntities(false, maxResults, firstResult);
    }

    private List<Historyorderdetail> findHistoryorderdetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historyorderdetail.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Historyorderdetail findHistoryorderdetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historyorderdetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoryorderdetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historyorderdetail> rt = cq.from(Historyorderdetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
