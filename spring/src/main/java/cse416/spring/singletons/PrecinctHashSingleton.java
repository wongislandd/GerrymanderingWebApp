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
            final long startTime = System.currentTimeMillis();
            precinctHashNC = getPrecinctHashFromDB(StateName.NORTH_CAROLINA);
            final long endTime = System.currentTimeMillis();
            System.out.println("LOADED PRECINCT HASH FROM THE DB IN " + (endTime-startTime) + "ms");
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
