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
import project.model.History;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import project.model.Product;
import project.model.jpa.controller.exceptions.IllegalOrphanException;
import project.model.jpa.controller.exceptions.NonexistentEntityException;
import project.model.jpa.controller.exceptions.PreexistingEntityException;
import project.model.jpa.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Admin
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account email = product.getEmail();
            if (email != null) {
                email = em.getReference(email.getClass(), email.getEmail());
                product.setEmail(email);
            }
            History history = product.getHistory();
            if (history != null) {
                history = em.getReference(history.getClass(), history.getProductcode());
                product.setHistory(history);
            }
            em.persist(product);
            if (email != null) {
                email.getProductList().add(product);
                email = em.merge(email);
            }
            if (history != null) {
                Product oldProductOfHistory = history.getProduct();
                if (oldProductOfHistory != null) {
                    oldProductOfHistory.setHistory(null);
                    oldProductOfHistory = em.merge(oldProductOfHistory);
                }
                history.setProduct(product);
                history = em.merge(history);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProduct(product.getProductcode()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product persistentProduct = em.find(Product.class, product.getProductcode());
            Account emailOld = persistentProduct.getEmail();
            Account emailNew = product.getEmail();
            History historyOld = persistentProduct.getHistory();
            History historyNew = product.getHistory();
            List<String> illegalOrphanMessages = null;
            if (historyOld != null && !historyOld.equals(historyNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain History " + historyOld + " since its product field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (emailNew != null) {
                emailNew = em.getReference(emailNew.getClass(), emailNew.getEmail());
                product.setEmail(emailNew);
            }
            if (historyNew != null) {
                historyNew = em.getReference(historyNew.getClass(), historyNew.getProductcode());
                product.setHistory(historyNew);
            }
            product = em.merge(product);
            if (emailOld != null && !emailOld.equals(emailNew)) {
                emailOld.getProductList().remove(product);
                emailOld = em.merge(emailOld);
            }
            if (emailNew != null && !emailNew.equals(emailOld)) {
                emailNew.getProductList().add(product);
                emailNew = em.merge(emailNew);
            }
            if (historyNew != null && !historyNew.equals(historyOld)) {
                Product oldProductOfHistory = historyNew.getProduct();
                if (oldProductOfHistory != null) {
                    oldProductOfHistory.setHistory(null);
                    oldProductOfHistory = em.merge(oldProductOfHistory);
                }
                historyNew.setProduct(product);
                historyNew = em.merge(historyNew);
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
                String id = product.getProductcode();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductcode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            History historyOrphanCheck = product.getHistory();
            if (historyOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the History " + historyOrphanCheck + " in its history field has a non-nullable product field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Account email = product.getEmail();
            if (email != null) {
                email.getProductList().remove(product);
                email = em.merge(email);
            }
            em.remove(product);
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

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
