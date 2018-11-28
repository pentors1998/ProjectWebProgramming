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
import project.jpa.model.Historyorderdetail;
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

    public void create(Historyorder historyorder) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (historyorder.getHistoryorderdetailList() == null) {
            historyorder.setHistoryorderdetailList(new ArrayList<Historyorderdetail>());
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
            List<Historyorderdetail> attachedHistoryorderdetailList = new ArrayList<Historyorderdetail>();
            for (Historyorderdetail historyorderdetailListHistoryorderdetailToAttach : historyorder.getHistoryorderdetailList()) {
                historyorderdetailListHistoryorderdetailToAttach = em.getReference(historyorderdetailListHistoryorderdetailToAttach.getClass(), historyorderdetailListHistoryorderdetailToAttach.getOrderdetailid());
                attachedHistoryorderdetailList.add(historyorderdetailListHistoryorderdetailToAttach);
            }
            historyorder.setHistoryorderdetailList(attachedHistoryorderdetailList);
            em.persist(historyorder);
            if (email != null) {
                email.getHistoryorderList().add(historyorder);
                email = em.merge(email);
            }
            for (Historyorderdetail historyorderdetailListHistoryorderdetail : historyorder.getHistoryorderdetailList()) {
                Historyorder oldOrderidOfHistoryorderdetailListHistoryorderdetail = historyorderdetailListHistoryorderdetail.getOrderid();
                historyorderdetailListHistoryorderdetail.setOrderid(historyorder);
                historyorderdetailListHistoryorderdetail = em.merge(historyorderdetailListHistoryorderdetail);
                if (oldOrderidOfHistoryorderdetailListHistoryorderdetail != null) {
                    oldOrderidOfHistoryorderdetailListHistoryorderdetail.getHistoryorderdetailList().remove(historyorderdetailListHistoryorderdetail);
                    oldOrderidOfHistoryorderdetailListHistoryorderdetail = em.merge(oldOrderidOfHistoryorderdetailListHistoryorderdetail);
                }
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

    public void edit(Historyorder historyorder) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historyorder persistentHistoryorder = em.find(Historyorder.class, historyorder.getOrderid());
            Account emailOld = persistentHistoryorder.getEmail();
            Account emailNew = historyorder.getEmail();
            List<Historyorderdetail> historyorderdetailListOld = persistentHistoryorder.getHistoryorderdetailList();
            List<Historyorderdetail> historyorderdetailListNew = historyorder.getHistoryorderdetailList();
            List<String> illegalOrphanMessages = null;
            for (Historyorderdetail historyorderdetailListOldHistoryorderdetail : historyorderdetailListOld) {
                if (!historyorderdetailListNew.contains(historyorderdetailListOldHistoryorderdetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historyorderdetail " + historyorderdetailListOldHistoryorderdetail + " since its orderid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (emailNew != null) {
                emailNew = em.getReference(emailNew.getClass(), emailNew.getEmail());
                historyorder.setEmail(emailNew);
            }
            List<Historyorderdetail> attachedHistoryorderdetailListNew = new ArrayList<Historyorderdetail>();
            for (Historyorderdetail historyorderdetailListNewHistoryorderdetailToAttach : historyorderdetailListNew) {
                historyorderdetailListNewHistoryorderdetailToAttach = em.getReference(historyorderdetailListNewHistoryorderdetailToAttach.getClass(), historyorderdetailListNewHistoryorderdetailToAttach.getOrderdetailid());
                attachedHistoryorderdetailListNew.add(historyorderdetailListNewHistoryorderdetailToAttach);
            }
            historyorderdetailListNew = attachedHistoryorderdetailListNew;
            historyorder.setHistoryorderdetailList(historyorderdetailListNew);
            historyorder = em.merge(historyorder);
            if (emailOld != null && !emailOld.equals(emailNew)) {
                emailOld.getHistoryorderList().remove(historyorder);
                emailOld = em.merge(emailOld);
            }
            if (emailNew != null && !emailNew.equals(emailOld)) {
                emailNew.getHistoryorderList().add(historyorder);
                emailNew = em.merge(emailNew);
            }
            for (Historyorderdetail historyorderdetailListNewHistoryorderdetail : historyorderdetailListNew) {
                if (!historyorderdetailListOld.contains(historyorderdetailListNewHistoryorderdetail)) {
                    Historyorder oldOrderidOfHistoryorderdetailListNewHistoryorderdetail = historyorderdetailListNewHistoryorderdetail.getOrderid();
                    historyorderdetailListNewHistoryorderdetail.setOrderid(historyorder);
                    historyorderdetailListNewHistoryorderdetail = em.merge(historyorderdetailListNewHistoryorderdetail);
                    if (oldOrderidOfHistoryorderdetailListNewHistoryorderdetail != null && !oldOrderidOfHistoryorderdetailListNewHistoryorderdetail.equals(historyorder)) {
                        oldOrderidOfHistoryorderdetailListNewHistoryorderdetail.getHistoryorderdetailList().remove(historyorderdetailListNewHistoryorderdetail);
                        oldOrderidOfHistoryorderdetailListNewHistoryorderdetail = em.merge(oldOrderidOfHistoryorderdetailListNewHistoryorderdetail);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
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
            List<String> illegalOrphanMessages = null;
            List<Historyorderdetail> historyorderdetailListOrphanCheck = historyorder.getHistoryorderdetailList();
            for (Historyorderdetail historyorderdetailListOrphanCheckHistoryorderdetail : historyorderdetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Historyorder (" + historyorder + ") cannot be destroyed since the Historyorderdetail " + historyorderdetailListOrphanCheckHistoryorderdetail + " in its historyorderdetailList field has a non-nullable orderid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Account email = historyorder.getEmail();
            if (email != null) {
                email.getHistoryorderList().remove(historyorder);
                email = em.merge(email);
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
