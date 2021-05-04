package cse416.spring.singletons;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmfSingleton {
    // TODO: Implement
    private static EntityManagerFactory emf;

    private EmfSingleton() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("orioles_db");
        }
        return emf;
    }
}
