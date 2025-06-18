package controladores;

import persistencia.RegistroComisiones;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
}
