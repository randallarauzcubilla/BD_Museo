package controladores;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import persistencia.Comisionestarjetas;

/**
 * @author Randall AC
 */
public class ComisionestarjetasJpaController {

    private EntityManagerFactory emf = null;

    public ComisionestarjetasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MUSEO_JPA");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear una nueva comisión
    public void create(Comisionestarjetas comision) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(comision);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Consultar todos los registros
    public Collection<Comisionestarjetas> findComisionesEntities() {
        return findComisionesEntities(true, 0, 0);
    }

    // Consultar bloques de datos
    public Collection<Comisionestarjetas> findComisionesEntities(int maxResults, int firstResult) {
        return findComisionesEntities(false, maxResults, firstResult);
    }

    // SELECT para recuperar datos
    private Collection<Comisionestarjetas> findComisionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Comisionestarjetas> cq = em.getCriteriaBuilder().createQuery(Comisionestarjetas.class);
            cq.select(cq.from(Comisionestarjetas.class));
            Query q = em.createQuery(cq);

            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return (Collection<Comisionestarjetas>) q.getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar datos de una comisión
    public void edit(Comisionestarjetas comision) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(comision);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar una comisión
    public void delete(Comisionestarjetas comision) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Comisionestarjetas poreliminar = em.find(Comisionestarjetas.class, comision.getIdComision());
            if (poreliminar != null) {
                em.remove(poreliminar);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}