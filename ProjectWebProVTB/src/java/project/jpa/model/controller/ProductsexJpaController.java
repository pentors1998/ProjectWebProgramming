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
import project.jpa.model.Productsex;
import project.jpa.model.controller.exceptions.IllegalOrphanException;
import project.jpa.model.controller.exceptions.NonexistentEntityException;
import project.jpa.model.controller.exceptions.PreexistingEntityException;
import project.jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Admin
 */
public class ProductsexJpaController implements Serializable {

    public ProductsexJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productsex productsex) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (productsex.getProductList() == null) {
            productsex.setProductList(new ArrayList<Product>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Product> attachedProductList = new ArrayList<Product>();
            for (Product productListProductToAttach : productsex.getProductList()) {
                productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getProductcode());
                attachedProductList.add(productListProductToAttach);
            }
            productsex.setProductList(attachedProductList);
            em.persist(productsex);
            for (Product productListProduct : productsex.getProductList()) {
                Productsex oldProductsexOfProductListProduct = productListProduct.getProductsex();
                productListProduct.setProductsex(productsex);
                productListProduct = em.merge(productListProduct);
                if (oldProductsexOfProductListProduct != null) {
                    oldProductsexOfProductListProduct.getProductList().remove(productListProduct);
                    oldProductsexOfProductListProduct = em.merge(oldProductsexOfProductListProduct);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProductsex(productsex.getProductsex()) != null) {
                throw new PreexistingEntityException("Productsex " + productsex + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productsex productsex) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Productsex persistentProductsex = em.find(Productsex.class, productsex.getProductsex());
            List<Product> productListOld = persistentProductsex.getProductList();
            List<Product> productListNew = productsex.getProductList();
            List<String> illegalOrphanMessages = null;
            for (Product productListOldProduct : productListOld) {
                if (!productListNew.contains(productListOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productListOldProduct + " since its productsex field is not nullable.");
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
            productsex.setProductList(productListNew);
            productsex = em.merge(productsex);
            for (Product productListNewProduct : productListNew) {
                if (!productListOld.contains(productListNewProduct)) {
                    Productsex oldProductsexOfProductListNewProduct = productListNewProduct.getProductsex();
                    productListNewProduct.setProductsex(productsex);
                    productListNewProduct = em.merge(productListNewProduct);
                    if (oldProductsexOfProductListNewProduct != null && !oldProductsexOfProductListNewProduct.equals(productsex)) {
                        oldProductsexOfProductListNewProduct.getProductList().remove(productListNewProduct);
                        oldProductsexOfProductListNewProduct = em.merge(oldProductsexOfProductListNewProduct);
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
                String id = productsex.getProductsex();
                if (findProductsex(id) == null) {
                    throw new NonexistentEntityException("The productsex with id " + id + " no longer exists.");
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
            Productsex productsex;
            try {
                productsex = em.getReference(Productsex.class, id);
                productsex.getProductsex();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productsex with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Product> productListOrphanCheck = productsex.getProductList();
            for (Product productListOrphanCheckProduct : productListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productsex (" + productsex + ") cannot be destroyed since the Product " + productListOrphanCheckProduct + " in its productList field has a non-nullable productsex field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(productsex);
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

    public List<Productsex> findProductsexEntities() {
        return findProductsexEntities(true, -1, -1);
    }

    public List<Productsex> findProductsexEntities(int maxResults, int firstResult) {
        return findProductsexEntities(false, maxResults, firstResult);
    }

    private List<Productsex> findProductsexEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productsex.class));
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

    public Productsex findProductsex(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productsex.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductsexCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productsex> rt = cq.from(Productsex.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
