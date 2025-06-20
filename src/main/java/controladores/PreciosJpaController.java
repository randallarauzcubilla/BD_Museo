package controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import persistencia.Precios;

/**
 * Controlador JPA para la gestión de precios
 *
 * @author Randall AC
 */
public class PreciosJpaController {

    private EntityManagerFactory emf = null;

    public PreciosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("MUSEO_JPA");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear un nuevo precio
    public void create(Precios precio) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(precio);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void create(Precios precio, EntityManager em) {
        em.persist(precio);
    }

    // Editar un precio existente
    public void edit(Precios precio) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(precio);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar un precio por ID
    public void delete(int idPrecio) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Precios precio = em.find(Precios.class, idPrecio);
            if (precio != null) {
                em.remove(precio);
                em.getTransaction().commit();
            } else {
                System.out.println("No se encontró el precio con ID: " + idPrecio);
            }
        } finally {
            em.close();
        }
    }

    // Obtener todos los precios
    public List<Precios> findPreciosEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Precios p", Precios.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Buscar un precio por ID
    public Precios findPrecio(int idPrecio) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Precios.class, idPrecio);
        } finally {
            em.close();
        }
    }

    public Precios findPrecioPorSalaYDia(int idSala, boolean esDomingo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Precios> query;
            if (esDomingo) {
                query = em.createQuery(
                        "SELECT p FROM Precios p WHERE p.idSala.idSala = :idSala AND p.precioDomingo > 0",
                        Precios.class
                );
            } else {
                query = em.createQuery(
                        "SELECT p FROM Precios p WHERE p.idSala.idSala = :idSala AND p.precioLunesSabado > 0",
                        Precios.class
                );
            }
            query.setParameter("idSala", idSala);
            return query.setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
