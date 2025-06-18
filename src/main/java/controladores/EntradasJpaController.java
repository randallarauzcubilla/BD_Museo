package controladores;

import java.util.Collections;
import java.util.List;
import javax.persistence.*;
import persistencia.Entradas;

public class EntradasJpaController {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MUSEO_JPA");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Crear una nueva entrada (cuando se vende)
    public void create(Entradas entrada) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entrada);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Entradas> findEntradas() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Entradas> query = em.createQuery("SELECT e FROM Entrada e", Entradas.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Entradas findEntradaById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entradas.class, id);
        } finally {
            em.close();
        }
    }

    public List<Entradas> findEntradasEntities() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Entradas e", Entradas.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Entradas> findByCodigoQR(String codigoQR) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Entradas e WHERE e.codigoQR = :codigoQR", Entradas.class)
                    .setParameter("codigoQR", codigoQR)
                    .getResultList(); 
        } catch (Exception e) {
            System.out.println("Error al buscar entradas por QR: " + e.getMessage());
            return Collections.emptyList(); 
        } finally {
            em.close();
        }
    }
}
