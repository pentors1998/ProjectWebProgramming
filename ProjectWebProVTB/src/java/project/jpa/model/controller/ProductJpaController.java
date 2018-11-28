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
import project.jpa.model.Productsex;
import project.jpa.model.Producttype;
import project.jpa.model.Historyorderdetail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import project.jpa.model.Product;
import project.jpa.model.controller.exceptions.IllegalOrphanException;
import project.jpa.model.controller.exceptions.NonexistentEntityException;
import project.jpa.model.controller.exceptions.PreexistingEntityException;
import project.jpa.model.controller.exceptions.RollbackFailureException;

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
        if (product.getHistoryorderdetailList() == null) {
            product.setHistoryorderdetailList(new ArrayList<Historyorderdetail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Productsex productsex = product.getProductsex();
            if (productsex != null) {
                productsex = em.getReference(productsex.getClass(), productsex.getProductsex());
                product.setProductsex(productsex);
            }
            Producttype producttype = product.getProducttype();
            if (producttype != null) {
                producttype = em.getReference(producttype.getClass(), producttype.getProducttype());
                product.setProducttype(producttype);
            }
            List<Historyorderdetail> attachedHistoryorderdetailList = new ArrayList<Historyorderdetail>();
            for (Historyorderdetail historyorderdetailListHistoryorderdetailToAttach : product.getHistoryorderdetailList()) {
                historyorderdetailListHistoryorderdetailToAttach = em.getReference(historyorderdetailListHistoryorderdetailToAttach.getClass(), historyorderdetailListHistoryorderdetailToAttach.getOrderdetailid());
                attachedHistoryorderdetailList.add(historyorderdetailListHistoryorderdetailToAttach);
            }
            product.setHistoryorderdetailList(attachedHistoryorderdetailList);
            em.persist(product);
            if (productsex != null) {
                productsex.getProductList().add(product);
                productsex = em.merge(productsex);
            }
            if (producttype != null) {
                producttype.getProductList().add(product);
                producttype = em.merge(producttype);
            }
            for (Historyorderdetail historyorderdetailListHistoryorderdetail : product.getHistoryorderdetailList()) {
                Product oldProductcodeOfHistoryorderdetailListHistoryorderdetail = historyorderdetailListHistoryorderdetail.getProductcode();
                historyorderdetailListHistoryorderdetail.setProductcode(product);
                historyorderdetailListHistoryorderdetail = em.merge(historyorderdetailListHistoryorderdetail);
                if (oldProductcodeOfHistoryorderdetailListHistoryorderdetail != null) {
                    oldProductcodeOfHistoryorderdetailListHistoryorderdetail.getHistoryorderdetailList().remove(historyorderdetailListHistoryorderdetail);
                    oldProductcodeOfHistoryorderdetailListHistoryorderdetail = em.merge(oldProductcodeOfHistoryorderdetailListHistoryorderdetail);
                }
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
            Productsex productsexOld = persistentProduct.getProductsex();
            Productsex productsexNew = product.getProductsex();
            Producttype producttypeOld = persistentProduct.getProducttype();
            Producttype producttypeNew = product.getProducttype();
            List<Historyorderdetail> historyorderdetailListOld = persistentProduct.getHistoryorderdetailList();
            List<Historyorderdetail> historyorderdetailListNew = product.getHistoryorderdetailList();
            List<String> illegalOrphanMessages = null;
            for (Historyorderdetail historyorderdetailListOldHistoryorderdetail : historyorderdetailListOld) {
                if (!historyorderdetailListNew.contains(historyorderdetailListOldHistoryorderdetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historyorderdetail " + historyorderdetailListOldHistoryorderdetail + " since its productcode field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (productsexNew != null) {
                productsexNew = em.getReference(productsexNew.getClass(), productsexNew.getProductsex());
                product.setProductsex(productsexNew);
            }
            if (producttypeNew != null) {
                producttypeNew = em.getReference(producttypeNew.getClass(), producttypeNew.getProducttype());
                product.setProducttype(producttypeNew);
            }
            List<Historyorderdetail> attachedHistoryorderdetailListNew = new ArrayList<Historyorderdetail>();
            for (Historyorderdetail historyorderdetailListNewHistoryorderdetailToAttach : historyorderdetailListNew) {
                historyorderdetailListNewHistoryorderdetailToAttach = em.getReference(historyorderdetailListNewHistoryorderdetailToAttach.getClass(), historyorderdetailListNewHistoryorderdetailToAttach.getOrderdetailid());
                attachedHistoryorderdetailListNew.add(historyorderdetailListNewHistoryorderdetailToAttach);
            }
            historyorderdetailListNew = attachedHistoryorderdetailListNew;
            product.setHistoryorderdetailList(historyorderdetailListNew);
            product = em.merge(product);
            if (productsexOld != null && !productsexOld.equals(productsexNew)) {
                productsexOld.getProductList().remove(product);
                productsexOld = em.merge(productsexOld);
            }
            if (productsexNew != null && !productsexNew.equals(productsexOld)) {
                productsexNew.getProductList().add(product);
                productsexNew = em.merge(productsexNew);
            }
            if (producttypeOld != null && !producttypeOld.equals(producttypeNew)) {
                producttypeOld.getProductList().remove(product);
                producttypeOld = em.merge(producttypeOld);
            }
            if (producttypeNew != null && !producttypeNew.equals(producttypeOld)) {
                producttypeNew.getProductList().add(product);
                producttypeNew = em.merge(producttypeNew);
            }
            for (Historyorderdetail historyorderdetailListNewHistoryorderdetail : historyorderdetailListNew) {
                if (!historyorderdetailListOld.contains(historyorderdetailListNewHistoryorderdetail)) {
                    Product oldProductcodeOfHistoryorderdetailListNewHistoryorderdetail = historyorderdetailListNewHistoryorderdetail.getProductcode();
                    historyorderdetailListNewHistoryorderdetail.setProductcode(product);
                    historyorderdetailListNewHistoryorderdetail = em.merge(historyorderdetailListNewHistoryorderdetail);
                    if (oldProductcodeOfHistoryorderdetailListNewHistoryorderdetail != null && !oldProductcodeOfHistoryorderdetailListNewHistoryorderdetail.equals(product)) {
                        oldProductcodeOfHistoryorderdetailListNewHistoryorderdetail.getHistoryorderdetailList().remove(historyorderdetailListNewHistoryorderdetail);
                        oldProductcodeOfHistoryorderdetailListNewHistoryorderdetail = em.merge(oldProductcodeOfHistoryorderdetailListNewHistoryorderdetail);
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
            List<Historyorderdetail> historyorderdetailListOrphanCheck = product.getHistoryorderdetailList();
            for (Historyorderdetail historyorderdetailListOrphanCheckHistoryorderdetail : historyorderdetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Historyorderdetail " + historyorderdetailListOrphanCheckHistoryorderdetail + " in its historyorderdetailList field has a non-nullable productcode field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Productsex productsex = product.getProductsex();
            if (productsex != null) {
                productsex.getProductList().remove(product);
                productsex = em.merge(productsex);
            }
            Producttype producttype = product.getProducttype();
            if (producttype != null) {
                producttype.getProductList().remove(product);
                producttype = em.merge(producttype);
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

    public List<Product> findByProductBrandname(String productBrandname) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Product.findByProductbrandname");
            query.setParameter("productbrandname", "%" + productBrandname.toLowerCase() + "%");
            return query.getResultList();
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
