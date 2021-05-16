package cse416.spring.singletons;

import cse416.spring.enums.StateName;
import cse416.spring.models.county.County;
import cse416.spring.service.CountyServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;

public class CountiesSetSingleton {
    private static Set<County> countiesNC = null;
    private static Set<County> countiesLA = null;
    private static Set<County> countiesAL = null;

    private CountiesSetSingleton() {
    }

    private static Set<County> getCountiesFromDB(StateName state) {
        EntityManagerFactory emf = EmfSingleton.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Set<County> counties = new CountyServiceImpl(em).findByStateName(state);
        em.close();
        return counties;
    }

    private static Set<County> getCountiesNC() {
        if (countiesNC == null) {
            countiesNC = getCountiesFromDB(StateName.NORTH_CAROLINA);
        }
        return countiesNC;
    }

    private static Set<County> getCountiesLA() {
        if (countiesLA == null) {
            countiesLA = getCountiesFromDB(StateName.LOUISIANA);
        }
        return countiesLA;
    }

    private static Set<County> getCountiesAL() {
        if (countiesAL == null) {
            countiesAL = getCountiesFromDB(StateName.ALABAMA);
        }
        return countiesAL;
    }

    public static Set<County> getCountiesSet(StateName state) {
        switch (state) {
            case NORTH_CAROLINA:
                return getCountiesNC();
            case LOUISIANA:
                return getCountiesLA();
            default:
                return getCountiesAL();
        }
    }
}
