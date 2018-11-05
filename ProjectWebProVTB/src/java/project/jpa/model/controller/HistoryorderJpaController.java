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
import project.jpa.model.Account;
import project.jpa.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import project.jpa.model.Historyorder;
import project.jpa.model.controller.exceptions.IllegalOrphanException;
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

    public void create(Historyorder historyorder) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Product productOrphanCheck = historyorder.getProduct();
        if (productOrphanCheck != null) {
            Historyorder oldHistoryorderOfProduct = productOrphanCheck.getHistoryorder();
            if (oldHistoryorderOfProduct != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Product " + productOrphanCheck + " already has an item of type Historyorder whose product column cannot be null. Please make another selection for the product field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account email = historyorder.getEmail();
            if (email != null) {
                email = em.getReference(email.getClass(), email.getEmail());
                historyorder.setEmail(email);
            }
            Product product = historyorder.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProductcode());
                historyorder.setProduct(product);
            }
            em.persist(historyorder);
            if (email != null) {
                email.getHistoryorderList().add(historyorder);
                email = em.merge(email);
            }
            if (product != null) {
                product.setHistoryorder(historyorder);
                product = em.merge(product);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistoryorder(historyorder.getProductcode()) != null) {
                throw new PreexistingEntityException("Historyorder " + historyorder + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historyorder historyorder) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historyorder persistentHistoryorder = em.find(Historyorder.class, historyorder.getProductcode());
            Account emailOld = persistentHistoryorder.getEmail();
            Account emailNew = historyorder.getEmail();
            Product productOld = persistentHistoryorder.getProduct();
            Product productNew = historyorder.getProduct();
            List<String> illegalOrphanMessages = null;
            if (productNew != null && !productNew.equals(productOld)) {
                Historyorder oldHistoryorderOfProduct = productNew.getHistoryorder();
                if (oldHistoryorderOfProduct != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Product " + productNew + " already has an item of type Historyorder whose product column cannot be null. Please make another selection for the product field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (emailNew != null) {
                emailNew = em.getReference(emailNew.getClass(), emailNew.getEmail());
                historyorder.setEmail(emailNew);
            }
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProductcode());
                historyorder.setProduct(productNew);
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
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.setHistoryorder(null);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.setHistoryorder(historyorder);
                productNew = em.merge(productNew);
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
                String id = historyorder.getProductcode();
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

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historyorder historyorder;
            try {
                historyorder = em.getReference(Historyorder.class, id);
                historyorder.getProductcode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historyorder with id " + id + " no longer exists.", enfe);
            }
            Account email = historyorder.getEmail();
            if (email != null) {
                email.getHistoryorderList().remove(historyorder);
                email = em.merge(email);
            }
            Product product = historyorder.getProduct();
            if (product != null) {
                product.setHistoryorder(null);
                product = em.merge(product);
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

    public Historyorder findHistoryorder(String id) {
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
