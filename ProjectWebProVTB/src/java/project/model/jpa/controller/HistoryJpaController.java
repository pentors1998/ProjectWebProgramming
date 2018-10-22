/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model.jpa.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import project.model.Account;
import project.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import project.model.History;
import project.model.jpa.controller.exceptions.IllegalOrphanException;
import project.model.jpa.controller.exceptions.NonexistentEntityException;
import project.model.jpa.controller.exceptions.PreexistingEntityException;
import project.model.jpa.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Admin
 */
public class HistoryJpaController implements Serializable {

    public HistoryJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(History history) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Product productOrphanCheck = history.getProduct();
        if (productOrphanCheck != null) {
            History oldHistoryOfProduct = productOrphanCheck.getHistory();
            if (oldHistoryOfProduct != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Product " + productOrphanCheck + " already has an item of type History whose product column cannot be null. Please make another selection for the product field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account email = history.getEmail();
            if (email != null) {
                email = em.getReference(email.getClass(), email.getEmail());
                history.setEmail(email);
            }
            Product product = history.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProductcode());
                history.setProduct(product);
            }
            em.persist(history);
            if (email != null) {
                email.getHistoryList().add(history);
                email = em.merge(email);
            }
            if (product != null) {
                product.setHistory(history);
                product = em.merge(product);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistory(history.getProductcode()) != null) {
                throw new PreexistingEntityException("History " + history + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(History history) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            History persistentHistory = em.find(History.class, history.getProductcode());
            Account emailOld = persistentHistory.getEmail();
            Account emailNew = history.getEmail();
            Product productOld = persistentHistory.getProduct();
            Product productNew = history.getProduct();
            List<String> illegalOrphanMessages = null;
            if (productNew != null && !productNew.equals(productOld)) {
                History oldHistoryOfProduct = productNew.getHistory();
                if (oldHistoryOfProduct != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Product " + productNew + " already has an item of type History whose product column cannot be null. Please make another selection for the product field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (emailNew != null) {
                emailNew = em.getReference(emailNew.getClass(), emailNew.getEmail());
                history.setEmail(emailNew);
            }
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProductcode());
                history.setProduct(productNew);
            }
            history = em.merge(history);
            if (emailOld != null && !emailOld.equals(emailNew)) {
                emailOld.getHistoryList().remove(history);
                emailOld = em.merge(emailOld);
            }
            if (emailNew != null && !emailNew.equals(emailOld)) {
                emailNew.getHistoryList().add(history);
                emailNew = em.merge(emailNew);
            }
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.setHistory(null);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.setHistory(history);
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
                String id = history.getProductcode();
                if (findHistory(id) == null) {
                    throw new NonexistentEntityException("The history with id " + id + " no longer exists.");
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
            History history;
            try {
                history = em.getReference(History.class, id);
                history.getProductcode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The history with id " + id + " no longer exists.", enfe);
            }
            Account email = history.getEmail();
            if (email != null) {
                email.getHistoryList().remove(history);
                email = em.merge(email);
            }
            Product product = history.getProduct();
            if (product != null) {
                product.setHistory(null);
                product = em.merge(product);
            }
            em.remove(history);
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

    public List<History> findHistoryEntities() {
        return findHistoryEntities(true, -1, -1);
    }

    public List<History> findHistoryEntities(int maxResults, int firstResult) {
        return findHistoryEntities(false, maxResults, firstResult);
    }

    private List<History> findHistoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(History.class));
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

    public History findHistory(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(History.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<History> rt = cq.from(History.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
