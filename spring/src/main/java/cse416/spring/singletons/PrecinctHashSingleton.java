package cse416.spring.singletons;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.service.PrecinctServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;

public class PrecinctHashSingleton {
    private static HashMap<Integer, Precinct> precinctHashNC = null;

    private PrecinctHashSingleton() {
    }

    private static HashMap<Integer, Precinct> getPrecinctHashFromDB(StateName state) {
        EntityManagerFactory emf = EmfSingleton.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        HashMap<Integer, Precinct> hashmap = new PrecinctServiceImpl(em).getPrecinctHashMapByState(state);
        em.close();

        return hashmap;
    }

    private static HashMap<Integer, Precinct> getPrecinctHashNC() {
        if (precinctHashNC == null) {
            System.out.println("Initializing Precinct Hash Singleton");
            precinctHashNC = getPrecinctHashFromDB(StateName.NORTH_CAROLINA);
        }
        return precinctHashNC;
    }

    public static HashMap<Integer, Precinct> getPrecinctHash(StateName state) {
        switch (state) {
            case NORTH_CAROLINA:
                return getPrecinctHashNC();
            default:
                return getPrecinctHashNC();
        }
    }
}
