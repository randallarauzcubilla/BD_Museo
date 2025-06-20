package controladores;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import persistencia.Salas;
import persistencia.Valoraciones;

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

    public void create(Salas sala, EntityManager em) {
        em.persist(sala);
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

    public List<Salas> findSalasByMuseo(int idMuseo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Salas> query = em.createQuery(
                    "SELECT s FROM Salas s WHERE s.idMuseo.idMuseo = :idMuseo", Salas.class);
            query.setParameter("idMuseo", idMuseo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Salas> obtenerSalasPorMuseo(int idMuseo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Salas> query = em.createQuery(
                    "SELECT s FROM Salas s WHERE s.idMuseo.idMuseo = :idMuseo", Salas.class);
            query.setParameter("idMuseo", idMuseo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Salas findSalasByNombre(String nombreSala) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Salas> query = em.createQuery(
                    "SELECT s FROM Salas s WHERE s.nombre = :nombre", Salas.class);
            query.setParameter("nombre", nombreSala);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Sala no encontrada: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public Salas findSalasById(int idSala) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salas.class, idSala);
        } finally {
            em.close();
        }
    }

    public List<Valoraciones> findValoracionesBySala(int idSala) {
        EntityManager em = getEntityManager();
        try {
            String query = "SELECT v FROM Valoraciones v WHERE v.idSala.idSala = :idSala";
            return em.createQuery(query, Valoraciones.class)
                    .setParameter("idSala", idSala)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Salas findSalaConPrecios(int idSala) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Salas> query = em.createQuery(
                    "SELECT s FROM Salas s LEFT JOIN FETCH s.preciosCollection WHERE s.idSala = :idSala",
                    Salas.class
            );
            query.setParameter("idSala", idSala);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
