package controladores;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import persistencia.Especies;

public class EspeciesJpaController {

    private EntityManagerFactory emf = null;

    public EspeciesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MUSEO_JPA");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear una nueva especie
    public void create(Especies especie) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(especie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Consultar todas las especies
    public Collection<Especies> findEspeciesEntities() {
        return findEspeciesEntities(true, 0, 0);
    }

    // Consultar especies con paginación
    public Collection<Especies> findEspeciesEntities(int maxResults, int firstResult) {
        return findEspeciesEntities(false, maxResults, firstResult);
    }

    private Collection<Especies> findEspeciesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Especies> cq = em.getCriteriaBuilder().createQuery(Especies.class);
            cq.select(cq.from(Especies.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return (Collection<Especies>) q.getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar datos de una especie
    public void edit(Especies especie) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(especie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar una especie
    public void delete(Especies especie) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Especies porEliminar = em.find(Especies.class, especie.getIdEspecie());
            if (porEliminar != null) {
                em.remove(porEliminar);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}