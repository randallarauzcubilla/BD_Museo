package controladores;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import persistencia.Salas;

public class SalasJpaController {

    private EntityManagerFactory emf = null;

    public SalasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MUSEO_JPA");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear una nueva sala
    public void create(Salas sala) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sala);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Consultar todas las salas
    public Collection<Salas> findSalasEntities() {
        return findSalasEntities(true, 0, 0);
    }

    // Consultar bloques de salas (paginación)
    public Collection<Salas> findSalasEntities(int maxResults, int firstResult) {
        return findSalasEntities(false, maxResults, firstResult);
    }

    // Consulta interna para salas
    private Collection<Salas> findSalasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Salas> cq = em.getCriteriaBuilder().createQuery(Salas.class);
            cq.select(cq.from(Salas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return (Collection<Salas>) q.getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar datos de una sala
    public void edit(Salas sala) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(sala);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar una sala
    public void delete(Salas sala) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Salas porEliminar = em.find(Salas.class, sala.getIdSala());
            if (porEliminar != null) {
                em.remove(porEliminar);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}