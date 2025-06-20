package controladores;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import persistencia.ImagenesSalas;
import persistencia.Salas;

/**
 *
 * @author Randall AC
 */
public class ImagenesSalasJpaController {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MUSEO_JPA");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<ImagenesSalas> findBySala(int idSala) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<ImagenesSalas> query = em.createQuery(
                    "SELECT i FROM ImagenesSalas i WHERE i.idSala.idSala = :idSala", ImagenesSalas.class);
            query.setParameter("idSala", idSala);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<ImagenesSalas> findByTipo(String tipo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<ImagenesSalas> query = em.createQuery(
                    "SELECT i FROM ImagenesSalas i WHERE i.tipo = :tipo", ImagenesSalas.class);
            query.setParameter("tipo", tipo);  // Usamos directamente el tipo String
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void agregarImagenASala(int idSala, String rutaImagen, String descripcion, String tipo) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Salas sala = em.find(Salas.class, idSala);
            if (sala != null) {
                ImagenesSalas imagen = new ImagenesSalas();
                imagen.setIdSala(sala);
                imagen.setUrlImagen(rutaImagen);
                imagen.setDescripcion(descripcion);

                // Validar el tipo y asignarlo correctamente
                if ("Tematica".equalsIgnoreCase(tipo) || "Especie".equalsIgnoreCase(tipo)) {
                    imagen.setTipo(tipo);  // Asignar directamente el valor String
                } else {
                    System.out.println("Error: Tipo de imagen no válido.");
                    return;
                }

                em.persist(imagen);
                em.getTransaction().commit();
                System.out.println("Imagen agregada correctamente.");
            } else {
                System.out.println("Sala con id " + idSala + " no encontrada.");
            }
        } finally {
            em.close();
        }
    }

    public List<ImagenesSalas> findBySalaAndTipo(int idSala, String tipo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<ImagenesSalas> query = em.createQuery(
                    "SELECT i FROM ImagenesSalas i WHERE i.idSala.idSala = :idSala AND i.tipo = :tipo",
                    ImagenesSalas.class
            );
            query.setParameter("idSala", idSala);
            query.setParameter("tipo", tipo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
