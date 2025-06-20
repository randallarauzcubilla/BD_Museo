package com.mycompany.bd_museomahn_proyecto2;
import controladores.PreciosJpaController;
import controladores.SalasJpaController;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import persistencia.Precios;
import persistencia.Salas;

/**
 *
 * @author Randall AC
 */
public class SalaService {

    private final SalasJpaController salasCtrl = new SalasJpaController();
    private final PreciosJpaController preciosCtrl = new PreciosJpaController();
   
    public void crearSalaConPrecio(Salas sala, Precios precio) {
    EntityManager em = Persistence.createEntityManagerFactory("MUSEO_JPA").createEntityManager();
    try {
        em.getTransaction().begin();

        // Usar el mismo EM para ambas operaciones
        salasCtrl.create(sala, em);
        precio.setIdSala(sala);
        preciosCtrl.create(precio, em);

        em.getTransaction().commit();
    } catch (Exception e) {
        em.getTransaction().rollback();
        System.out.println("¡Error al crear la sala con precio! " + e.getMessage());
    } finally {
        em.close();
    }
}
}
