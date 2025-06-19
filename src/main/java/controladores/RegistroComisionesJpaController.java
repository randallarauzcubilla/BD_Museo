package controladores;

import java.time.LocalDate;
import java.util.List;
import persistencia.RegistroComisiones;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class RegistroComisionesJpaController {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MUSEO_JPA");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroComisiones comision) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(comision);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<RegistroComisiones> obtenerComisionesPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        EntityManager em = getEntityManager();
        try {
            String query = "SELECT c FROM Comision c WHERE c.fecha >= :fechaInicio AND c.fecha <= :fechaFin";
            TypedQuery<RegistroComisiones> typedQuery = em.createQuery(query, RegistroComisiones.class);
            typedQuery.setParameter("fechaInicio", fechaInicio);
            typedQuery.setParameter("fechaFin", fechaFin);

            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }
}
