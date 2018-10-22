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
import project.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import project.model.Account;
import project.model.History;
import project.model.jpa.controller.exceptions.IllegalOrphanException;
import project.model.jpa.controller.exceptions.NonexistentEntityException;
import project.model.jpa.controller.exceptions.PreexistingEntityException;
import project.model.jpa.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Admin
 */
public class AccountJpaController implements Serializable {

    public AccountJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Account account) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (account.getProductList() == null) {
            account.setProductList(new ArrayList<Product>());
        }
        if (account.getHistoryList() == null) {
            account.setHistoryList(new ArrayList<History>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Product> attachedProductList = new ArrayList<Product>();
            for (Product productListProductToAttach : account.getProductList()) {
                productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getProductcode());
                attachedProductList.add(productListProductToAttach);
            }
            account.setProductList(attachedProductList);
            List<History> attachedHistoryList = new ArrayList<History>();
            for (History historyListHistoryToAttach : account.getHistoryList()) {
                historyListHistoryToAttach = em.getReference(historyListHistoryToAttach.getClass(), historyListHistoryToAttach.getProductcode());
                attachedHistoryList.add(historyListHistoryToAttach);
            }
            account.setHistoryList(attachedHistoryList);
            em.persist(account);
            for (Product productListProduct : account.getProductList()) {
                Account oldEmailOfProductListProduct = productListProduct.getEmail();
                productListProduct.setEmail(account);
                productListProduct = em.merge(productListProduct);
                if (oldEmailOfProductListProduct != null) {
                    oldEmailOfProductListProduct.getProductList().remove(productListProduct);
                    oldEmailOfProductListProduct = em.merge(oldEmailOfProductListProduct);
                }
            }
            for (History historyListHistory : account.getHistoryList()) {
                Account oldEmailOfHistoryListHistory = historyListHistory.getEmail();
                historyListHistory.setEmail(account);
                historyListHistory = em.merge(historyListHistory);
                if (oldEmailOfHistoryListHistory != null) {
                    oldEmailOfHistoryListHistory.getHistoryList().remove(historyListHistory);
                    oldEmailOfHistoryListHistory = em.merge(oldEmailOfHistoryListHistory);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAccount(account.getEmail()) != null) {
                throw new PreexistingEntityException("Account " + account + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Account account) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account persistentAccount = em.find(Account.class, account.getEmail());
            List<Product> productListOld = persistentAccount.getProductList();
            List<Product> productListNew = account.getProductList();
            List<History> historyListOld = persistentAccount.getHistoryList();
            List<History> historyListNew = account.getHistoryList();
            List<String> illegalOrphanMessages = null;
            for (Product productListOldProduct : productListOld) {
                if (!productListNew.contains(productListOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productListOldProduct + " since its email field is not nullable.");
                }
            }
            for (History historyListOldHistory : historyListOld) {
                if (!historyListNew.contains(historyListOldHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain History " + historyListOldHistory + " since its email field is not nullable.");
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
            account.setProductList(productListNew);
            List<History> attachedHistoryListNew = new ArrayList<History>();
            for (History historyListNewHistoryToAttach : historyListNew) {
                historyListNewHistoryToAttach = em.getReference(historyListNewHistoryToAttach.getClass(), historyListNewHistoryToAttach.getProductcode());
                attachedHistoryListNew.add(historyListNewHistoryToAttach);
            }
            historyListNew = attachedHistoryListNew;
            account.setHistoryList(historyListNew);
            account = em.merge(account);
            for (Product productListNewProduct : productListNew) {
                if (!productListOld.contains(productListNewProduct)) {
                    Account oldEmailOfProductListNewProduct = productListNewProduct.getEmail();
                    productListNewProduct.setEmail(account);
                    productListNewProduct = em.merge(productListNewProduct);
                    if (oldEmailOfProductListNewProduct != null && !oldEmailOfProductListNewProduct.equals(account)) {
                        oldEmailOfProductListNewProduct.getProductList().remove(productListNewProduct);
                        oldEmailOfProductListNewProduct = em.merge(oldEmailOfProductListNewProduct);
                    }
                }
            }
            for (History historyListNewHistory : historyListNew) {
                if (!historyListOld.contains(historyListNewHistory)) {
                    Account oldEmailOfHistoryListNewHistory = historyListNewHistory.getEmail();
                    historyListNewHistory.setEmail(account);
                    historyListNewHistory = em.merge(historyListNewHistory);
                    if (oldEmailOfHistoryListNewHistory != null && !oldEmailOfHistoryListNewHistory.equals(account)) {
                        oldEmailOfHistoryListNewHistory.getHistoryList().remove(historyListNewHistory);
                        oldEmailOfHistoryListNewHistory = em.merge(oldEmailOfHistoryListNewHistory);
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
                String id = account.getEmail();
                if (findAccount(id) == null) {
                    throw new NonexistentEntityException("The account with id " + id + " no longer exists.");
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
            Account account;
            try {
                account = em.getReference(Account.class, id);
                account.getEmail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The account with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Product> productListOrphanCheck = account.getProductList();
            for (Product productListOrphanCheckProduct : productListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Product " + productListOrphanCheckProduct + " in its productList field has a non-nullable email field.");
            }
            List<History> historyListOrphanCheck = account.getHistoryList();
            for (History historyListOrphanCheckHistory : historyListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the History " + historyListOrphanCheckHistory + " in its historyList field has a non-nullable email field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(account);
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

    public List<Account> findAccountEntities() {
        return findAccountEntities(true, -1, -1);
    }

    public List<Account> findAccountEntities(int maxResults, int firstResult) {
        return findAccountEntities(false, maxResults, firstResult);
    }

    private List<Account> findAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Account.class));
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

    public Account findAccount(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Account.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Account> rt = cq.from(Account.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
