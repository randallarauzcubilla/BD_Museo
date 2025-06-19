package com.mycompany.bd_museomahn_proyecto2;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Date;

/**
 *
 * @author Randall AC
 */
public class ComisionesService {

    private final EntityManager em;

    public ComisionesService(EntityManager em) {
        this.em = em;
    }

    public List<Object[]> obtenerComisionesPorFechas(Date fechaInicio, Date fechaFin) {
        String jpql = "SELECT c.tipoTarjeta, SUM(r.valorCobrado) FROM RegistroComisiones r "
                + "JOIN r.idComision c "
                + "WHERE r.idEntrada.fechaCompra BETWEEN :fechaInicio AND :fechaFin "
                + "GROUP BY c.tipoTarjeta";
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
}
