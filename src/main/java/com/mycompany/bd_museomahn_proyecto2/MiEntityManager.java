package com.mycompany.bd_museomahn_proyecto2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Randall AC
 */
public class MiEntityManager {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MUSEO_JPA");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}