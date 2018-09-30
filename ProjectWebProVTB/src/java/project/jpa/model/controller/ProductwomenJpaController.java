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
import project.jpa.model.Productwomen;
import project.jpa.model.controller.exceptions.NonexistentEntityException;
import project.jpa.model.controller.exceptions.PreexistingEntityException;
import project.jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Admin
 */
public class ProductwomenJpaController implements Serializable {

    public ProductwomenJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productwomen productwomen) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(productwomen);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProductwomen(productwomen.getProductcode()) != null) {
                throw new PreexistingEntityException("Productwomen " + productwomen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productwomen productwomen) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            productwomen = em.merge(productwomen);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = productwomen.getProductcode();
                if (findProductwomen(id) == null) {
                    throw new NonexistentEntityException("The productwomen with id " + id + " no longer exists.");
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
            Productwomen productwomen;
            try {
                productwomen = em.getReference(Productwomen.class, id);
                productwomen.getProductcode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productwomen with id " + id + " no longer exists.", enfe);
            }
            em.remove(productwomen);
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

    public List<Productwomen> findProductwomenEntities() {
        return findProductwomenEntities(true, -1, -1);
    }

    public List<Productwomen> findProductwomenEntities(int maxResults, int firstResult) {
        return findProductwomenEntities(false, maxResults, firstResult);
    }

    private List<Productwomen> findProductwomenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productwomen.class));
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

    public Productwomen findProductwomen(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productwomen.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductwomenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productwomen> rt = cq.from(Productwomen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
