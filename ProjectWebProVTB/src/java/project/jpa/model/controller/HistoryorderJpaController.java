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
import project.jpa.model.Account;
import project.jpa.model.Historyorder;
import project.jpa.model.Product;
import project.jpa.model.controller.exceptions.NonexistentEntityException;
import project.jpa.model.controller.exceptions.PreexistingEntityException;
import project.jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Admin
 */
public class HistoryorderJpaController implements Serializable {

    public HistoryorderJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historyorder historyorder) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account email = historyorder.getEmail();
            if (email != null) {
                email = em.getReference(email.getClass(), email.getEmail());
                historyorder.setEmail(email);
            }
            Product productcode = historyorder.getProductcode();
            if (productcode != null) {
                productcode = em.getReference(productcode.getClass(), productcode.getProductcode());
                historyorder.setProductcode(productcode);
            }
            em.persist(historyorder);
            if (email != null) {
                email.getHistoryorderList().add(historyorder);
                email = em.merge(email);
            }
            if (productcode != null) {
                productcode.getHistoryorderList().add(historyorder);
                productcode = em.merge(productcode);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistoryorder(historyorder.getOrderid()) != null) {
                throw new PreexistingEntityException("Historyorder " + historyorder + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historyorder historyorder) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historyorder persistentHistoryorder = em.find(Historyorder.class, historyorder.getOrderid());
            Account emailOld = persistentHistoryorder.getEmail();
            Account emailNew = historyorder.getEmail();
            Product productcodeOld = persistentHistoryorder.getProductcode();
            Product productcodeNew = historyorder.getProductcode();
            if (emailNew != null) {
                emailNew = em.getReference(emailNew.getClass(), emailNew.getEmail());
                historyorder.setEmail(emailNew);
            }
            if (productcodeNew != null) {
                productcodeNew = em.getReference(productcodeNew.getClass(), productcodeNew.getProductcode());
                historyorder.setProductcode(productcodeNew);
            }
            historyorder = em.merge(historyorder);
            if (emailOld != null && !emailOld.equals(emailNew)) {
                emailOld.getHistoryorderList().remove(historyorder);
                emailOld = em.merge(emailOld);
            }
            if (emailNew != null && !emailNew.equals(emailOld)) {
                emailNew.getHistoryorderList().add(historyorder);
                emailNew = em.merge(emailNew);
            }
            if (productcodeOld != null && !productcodeOld.equals(productcodeNew)) {
                productcodeOld.getHistoryorderList().remove(historyorder);
                productcodeOld = em.merge(productcodeOld);
            }
            if (productcodeNew != null && !productcodeNew.equals(productcodeOld)) {
                productcodeNew.getHistoryorderList().add(historyorder);
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
                Integer id = historyorder.getOrderid();
                if (findHistoryorder(id) == null) {
                    throw new NonexistentEntityException("The historyorder with id " + id + " no longer exists.");
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
            Historyorder historyorder;
            try {
                historyorder = em.getReference(Historyorder.class, id);
                historyorder.getOrderid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historyorder with id " + id + " no longer exists.", enfe);
            }
            Account email = historyorder.getEmail();
            if (email != null) {
                email.getHistoryorderList().remove(historyorder);
                email = em.merge(email);
            }
            Product productcode = historyorder.getProductcode();
            if (productcode != null) {
                productcode.getHistoryorderList().remove(historyorder);
                productcode = em.merge(productcode);
            }
            em.remove(historyorder);
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

    public List<Historyorder> findHistoryorderEntities() {
        return findHistoryorderEntities(true, -1, -1);
    }

    public List<Historyorder> findHistoryorderEntities(int maxResults, int firstResult) {
        return findHistoryorderEntities(false, maxResults, firstResult);
    }

    private List<Historyorder> findHistoryorderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historyorder.class));
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

    public Historyorder findHistoryorder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historyorder.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoryorderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historyorder> rt = cq.from(Historyorder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
