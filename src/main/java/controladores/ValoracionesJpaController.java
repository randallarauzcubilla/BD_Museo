package controladores;

import controladores.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import persistencia.Salas;
import persistencia.Valoraciones;

/**
 *
 * @author Randall AC
 */
public class ValoracionesJpaController {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MUSEO_JPA");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Valoraciones valoracion) throws PreexistingEntityException, Exception {
        if (valoracion.getIdValoracion() != null) {
            throw new PreexistingEntityException("Valoraciones " + valoracion + " already exists.");
        }
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Salas idSala = valoracion.getIdSala();
            if (idSala != null) {
                idSala = em.getReference(idSala.getClass(), idSala.getIdSala());
                valoracion.setIdSala(idSala);
            }
            em.persist(valoracion);
            em.getTransaction().commit();
        } catch (RuntimeException re) {
            em.getTransaction().rollback();
            throw re;
        } finally {
            em.close();
        }
    }

    public List<Valoraciones> findBySala(int idSala) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Valoraciones> query = em.createQuery(
                    "SELECT v FROM Valoraciones v WHERE v.idSala.idSala = :idSala", Valoraciones.class);
            query.setParameter("idSala", idSala);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Valoraciones> findValoracionesBySala(int idSala) {
        EntityManager em = getEntityManager();  // Asegúrate de obtener el EntityManager aquí
        try {
            String query = "SELECT v FROM Valoraciones v WHERE v.idSala.idSala = :idSala";
            return em.createQuery(query, Valoraciones.class)
                    .setParameter("idSala", idSala)
                    .getResultList();
        } finally {
            em.close();  // Cerrar el EntityManager después de usarlo
        }
    }
}
