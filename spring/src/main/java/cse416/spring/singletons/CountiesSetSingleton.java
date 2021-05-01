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

    private CountiesSetSingleton() {
    }

    private static Set<County> getCountiesFromDB(StateName state) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
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

    public static Set<County> getCountiesSet(StateName state) {
        switch (state) {
            case NORTH_CAROLINA:
                return getCountiesNC();
            default:
                return null;
        }
    }
}
