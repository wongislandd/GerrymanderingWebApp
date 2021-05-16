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
    private static HashMap<Integer, Precinct> precinctHashLA = null;
    private static HashMap<Integer, Precinct> precinctHashAL = null;

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

    private static HashMap<Integer, Precinct> getPrecinctHashLA() {
        if (precinctHashLA == null) {
            final long startTime = System.currentTimeMillis();
            precinctHashLA = getPrecinctHashFromDB(StateName.LOUISIANA);
            final long endTime = System.currentTimeMillis();
            System.out.println("LOADED PRECINCT HASH FROM THE DB IN " + (endTime-startTime) + "ms");
        }
        return precinctHashLA;
    }

    private static HashMap<Integer, Precinct> getPrecinctHashAL() {
        if (precinctHashAL == null) {
            final long startTime = System.currentTimeMillis();
            precinctHashAL = getPrecinctHashFromDB(StateName.ALABAMA);
            final long endTime = System.currentTimeMillis();
            System.out.println("LOADED PRECINCT HASH FROM THE DB IN " + (endTime-startTime) + "ms");
        }
        return precinctHashAL;
    }

    public static HashMap<Integer, Precinct> getPrecinctHash(StateName state) {
        switch (state) {
            case NORTH_CAROLINA:
                return getPrecinctHashNC();
            case LOUISIANA:
                return getPrecinctHashLA();
            default:
                return getPrecinctHashAL();
        }
    }
}
