package controladores;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import persistencia.Museos;

/**
 *
 * @author Randall AC
 */
public class MuseosJpaController {

    private EntityManagerFactory emf = null;

    public MuseosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MUSEO_JPA");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear un nuevo museo
    public void create(Museos museo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(museo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Consultar todos los registros
    public Collection<Museos> findMuseosEntities() {
        return findMuseosEntities(true, 0, 0);
    }

    // Consultar bloques de datos
    public Collection<Museos> findMuseosEntities(int maxResults, int firstResult) {
        return findMuseosEntities(false, maxResults, firstResult);
    }

    // SELECT para recuperar datos
    private Collection<Museos> findMuseosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Museos> cq = em.getCriteriaBuilder().createQuery(Museos.class);
            cq.select(cq.from(Museos.class));
            Query q = em.createQuery(cq);

            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return (Collection<Museos>) q.getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar datos de un museo
    public void edit(Museos museo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(museo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar un museo
    public void delete(Museos museo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Museos poreliminar = em.find(Museos.class, museo.getIdMuseo());
            if (poreliminar != null) {
                em.remove(poreliminar);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}
