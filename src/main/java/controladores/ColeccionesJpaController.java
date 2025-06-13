package controladores;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import persistencia.Colecciones;

public class ColeccionesJpaController {

    private EntityManagerFactory emf = null;

    public ColeccionesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MUSEO_JPA");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear una nueva colección
    public void create(Colecciones coleccion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(coleccion);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Consultar todas las colecciones
    public Collection<Colecciones> findColeccionesEntities() {
        return findColeccionesEntities(true, 0, 0);
    }

    // Consultar colecciones con paginación
    public Collection<Colecciones> findColeccionesEntities(int maxResults, int firstResult) {
        return findColeccionesEntities(false, maxResults, firstResult);
    }

    private Collection<Colecciones> findColeccionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Colecciones> cq = em.getCriteriaBuilder().createQuery(Colecciones.class);
            cq.select(cq.from(Colecciones.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return (Collection<Colecciones>) q.getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar datos de una colección
    public void edit(Colecciones coleccion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(coleccion);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar una colección
    public void delete(Colecciones coleccion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Colecciones porEliminar = em.find(Colecciones.class, coleccion.getIdColeccion());
            if (porEliminar != null) {
                em.remove(porEliminar);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}