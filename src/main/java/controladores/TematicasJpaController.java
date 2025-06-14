package controladores;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import persistencia.Tematicas;

public class TematicasJpaController {

    private EntityManagerFactory emf = null;

    public TematicasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MUSEO_JPA");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear una nueva temática
    public void create(Tematicas tematica) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tematica);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Consultar todas las temáticas
    public Collection<Tematicas> findTematicasEntities() {
        return findTematicasEntities(true, 0, 0);
    }

    // Consultar bloques de temáticas (paginación)
    public Collection<Tematicas> findTematicasEntities(int maxResults, int firstResult) {
        return findTematicasEntities(false, maxResults, firstResult);
    }

    // Consulta interna para temáticas
    private Collection<Tematicas> findTematicasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Tematicas> cq = em.getCriteriaBuilder().createQuery(Tematicas.class);
            cq.select(cq.from(Tematicas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return (Collection<Tematicas>) q.getResultList();
        } finally {
            em.close();
        }
    }

    // Buscar una temática por ID
    public Tematicas findTematica(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tematicas.class, id);
        } finally {
            em.close();
        }
    }

    // Actualizar datos de una temática
    public void edit(Tematicas tematica) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(tematica);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar una temática
    public void delete(Tematicas tematica) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Tematicas porEliminar = em.find(Tematicas.class, tematica.getIdTematica());
            if (porEliminar != null) {
                em.remove(porEliminar);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}