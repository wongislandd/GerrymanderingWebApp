package cse416.spring.helperclasses;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {
    private static final EntityManagerSingleton instance = new EntityManagerSingleton();

    private final EntityManagerFactory emfactory;
    private final EntityManager em;

    private EntityManagerSingleton(){
        emfactory = Persistence.createEntityManagerFactory( "orioles_db" );
        em = emfactory.createEntityManager();
    }
    public static EntityManagerSingleton getInstance() {
        return instance;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void shutdown() {
        em.close( );
        emfactory.close( );
    }
}
