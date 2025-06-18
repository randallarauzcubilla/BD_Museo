package controladores;

import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.EntradaSalas;
import persistencia.Entradas;

/**
 *
 * @author Randall AC
 */
public class EntradaSalasJpaController {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MUSEO_JPA");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EntradaSalas entradaSala) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entradaSala);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al crear EntradaSala: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<EntradaSalas> findByIdEntrada(Entradas entrada) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT es FROM EntradaSalas es WHERE es.idEntrada = :idEntrada", EntradaSalas.class)
                    .setParameter("idEntrada", entrada)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error al buscar salas para la entrada: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
}
