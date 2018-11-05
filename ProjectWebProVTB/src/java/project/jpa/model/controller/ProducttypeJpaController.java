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
import project.jpa.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import project.jpa.model.Producttype;
import project.jpa.model.controller.exceptions.IllegalOrphanException;
import project.jpa.model.controller.exceptions.NonexistentEntityException;
import project.jpa.model.controller.exceptions.PreexistingEntityException;
import project.jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Admin
 */
public class ProducttypeJpaController implements Serializable {

    public ProducttypeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producttype producttype) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (producttype.getProductList() == null) {
            producttype.setProductList(new ArrayList<Product>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Product> attachedProductList = new ArrayList<Product>();
            for (Product productListProductToAttach : producttype.getProductList()) {
                productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getProductcode());
                attachedProductList.add(productListProductToAttach);
            }
            producttype.setProductList(attachedProductList);
            em.persist(producttype);
            for (Product productListProduct : producttype.getProductList()) {
                Producttype oldProducttypeOfProductListProduct = productListProduct.getProducttype();
                productListProduct.setProducttype(producttype);
                productListProduct = em.merge(productListProduct);
                if (oldProducttypeOfProductListProduct != null) {
                    oldProducttypeOfProductListProduct.getProductList().remove(productListProduct);
                    oldProducttypeOfProductListProduct = em.merge(oldProducttypeOfProductListProduct);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProducttype(producttype.getProducttype()) != null) {
                throw new PreexistingEntityException("Producttype " + producttype + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producttype producttype) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producttype persistentProducttype = em.find(Producttype.class, producttype.getProducttype());
            List<Product> productListOld = persistentProducttype.getProductList();
            List<Product> productListNew = producttype.getProductList();
            List<String> illegalOrphanMessages = null;
            for (Product productListOldProduct : productListOld) {
                if (!productListNew.contains(productListOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productListOldProduct + " since its producttype field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Product> attachedProductListNew = new ArrayList<Product>();
            for (Product productListNewProductToAttach : productListNew) {
                productListNewProductToAttach = em.getReference(productListNewProductToAttach.getClass(), productListNewProductToAttach.getProductcode());
                attachedProductListNew.add(productListNewProductToAttach);
            }
            productListNew = attachedProductListNew;
            producttype.setProductList(productListNew);
            producttype = em.merge(producttype);
            for (Product productListNewProduct : productListNew) {
                if (!productListOld.contains(productListNewProduct)) {
                    Producttype oldProducttypeOfProductListNewProduct = productListNewProduct.getProducttype();
                    productListNewProduct.setProducttype(producttype);
                    productListNewProduct = em.merge(productListNewProduct);
                    if (oldProducttypeOfProductListNewProduct != null && !oldProducttypeOfProductListNewProduct.equals(producttype)) {
                        oldProducttypeOfProductListNewProduct.getProductList().remove(productListNewProduct);
                        oldProducttypeOfProductListNewProduct = em.merge(oldProducttypeOfProductListNewProduct);
                    }
                }
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
                String id = producttype.getProducttype();
                if (findProducttype(id) == null) {
                    throw new NonexistentEntityException("The producttype with id " + id + " no longer exists.");
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
            Producttype producttype;
            try {
                producttype = em.getReference(Producttype.class, id);
                producttype.getProducttype();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producttype with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Product> productListOrphanCheck = producttype.getProductList();
            for (Product productListOrphanCheckProduct : productListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producttype (" + producttype + ") cannot be destroyed since the Product " + productListOrphanCheckProduct + " in its productList field has a non-nullable producttype field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producttype);
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

    public List<Producttype> findProducttypeEntities() {
        return findProducttypeEntities(true, -1, -1);
    }

    public List<Producttype> findProducttypeEntities(int maxResults, int firstResult) {
        return findProducttypeEntities(false, maxResults, firstResult);
    }

    private List<Producttype> findProducttypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producttype.class));
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

    public Producttype findProducttype(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producttype.class, id);
        } finally {
            em.close();
        }
    }

    public int getProducttypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producttype> rt = cq.from(Producttype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
