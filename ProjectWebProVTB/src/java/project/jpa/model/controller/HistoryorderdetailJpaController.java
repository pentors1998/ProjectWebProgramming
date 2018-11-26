/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.jpa.model.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import project.jpa.model.Historyorder;
import project.jpa.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import project.jpa.model.Historyorderdetail;
import project.jpa.model.controller.exceptions.IllegalOrphanException;
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

    public void create(Historyorderdetail historyorderdetail) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Historyorder historyorderOrphanCheck = historyorderdetail.getHistoryorder();
        if (historyorderOrphanCheck != null) {
            Historyorderdetail oldHistoryorderdetailOfHistoryorder = historyorderOrphanCheck.getHistoryorderdetail();
            if (oldHistoryorderdetailOfHistoryorder != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Historyorder " + historyorderOrphanCheck + " already has an item of type Historyorderdetail whose historyorder column cannot be null. Please make another selection for the historyorder field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historyorder historyorder = historyorderdetail.getHistoryorder();
            if (historyorder != null) {
                historyorder = em.getReference(historyorder.getClass(), historyorder.getOrderid());
                historyorderdetail.setHistoryorder(historyorder);
            }
            Product productcode = historyorderdetail.getProductcode();
            if (productcode != null) {
                productcode = em.getReference(productcode.getClass(), productcode.getProductcode());
                historyorderdetail.setProductcode(productcode);
            }
            em.persist(historyorderdetail);
            if (historyorder != null) {
                historyorder.setHistoryorderdetail(historyorderdetail);
                historyorder = em.merge(historyorder);
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
            if (findHistoryorderdetail(historyorderdetail.getOrderid()) != null) {
                throw new PreexistingEntityException("Historyorderdetail " + historyorderdetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historyorderdetail historyorderdetail) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historyorderdetail persistentHistoryorderdetail = em.find(Historyorderdetail.class, historyorderdetail.getOrderid());
            Historyorder historyorderOld = persistentHistoryorderdetail.getHistoryorder();
            Historyorder historyorderNew = historyorderdetail.getHistoryorder();
            Product productcodeOld = persistentHistoryorderdetail.getProductcode();
            Product productcodeNew = historyorderdetail.getProductcode();
            List<String> illegalOrphanMessages = null;
            if (historyorderNew != null && !historyorderNew.equals(historyorderOld)) {
                Historyorderdetail oldHistoryorderdetailOfHistoryorder = historyorderNew.getHistoryorderdetail();
                if (oldHistoryorderdetailOfHistoryorder != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Historyorder " + historyorderNew + " already has an item of type Historyorderdetail whose historyorder column cannot be null. Please make another selection for the historyorder field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (historyorderNew != null) {
                historyorderNew = em.getReference(historyorderNew.getClass(), historyorderNew.getOrderid());
                historyorderdetail.setHistoryorder(historyorderNew);
            }
            if (productcodeNew != null) {
                productcodeNew = em.getReference(productcodeNew.getClass(), productcodeNew.getProductcode());
                historyorderdetail.setProductcode(productcodeNew);
            }
            historyorderdetail = em.merge(historyorderdetail);
            if (historyorderOld != null && !historyorderOld.equals(historyorderNew)) {
                historyorderOld.setHistoryorderdetail(null);
                historyorderOld = em.merge(historyorderOld);
            }
            if (historyorderNew != null && !historyorderNew.equals(historyorderOld)) {
                historyorderNew.setHistoryorderdetail(historyorderdetail);
                historyorderNew = em.merge(historyorderNew);
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
                Integer id = historyorderdetail.getOrderid();
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
                historyorderdetail.getOrderid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historyorderdetail with id " + id + " no longer exists.", enfe);
            }
            Historyorder historyorder = historyorderdetail.getHistoryorder();
            if (historyorder != null) {
                historyorder.setHistoryorderdetail(null);
                historyorder = em.merge(historyorder);
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
