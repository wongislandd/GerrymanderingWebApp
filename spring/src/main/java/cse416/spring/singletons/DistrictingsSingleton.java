package cse416.spring.singletons;

import cse416.spring.helperclasses.DistrictingConstraints;
import cse416.spring.models.districting.Districting;
import cse416.spring.service.DistrictingServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.HashSet;

public class DistrictingsSingleton {
    private static Collection<Districting> districtings;
    private static long currentJobId = -1;

    private DistrictingsSingleton() {}

    private static Collection<Districting> getDistrictingsFromDB(long jobId) {
        EntityManagerFactory emf = EmfSingleton.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Collection<Districting> districtings = new DistrictingServiceImpl(em).findByJob(jobId);
        em.close();
        return districtings;
    }

    public static Collection<Districting> getDistrictings(long jobId) {
        if (currentJobId != jobId || districtings == null || districtings.size() == 0) {
            System.out.println("Initializing Districtings Singleton");
            final long startTime = System.currentTimeMillis();
            districtings = getDistrictingsFromDB(jobId);
            currentJobId = jobId;
            final long endTime = System.currentTimeMillis();
            System.out.println("LOADED DISTRICTINGS FROM THE DB IN " + (endTime-startTime) + "ms");
        }
        return districtings;
    }

    private static boolean inMajorityMinorityDistrictRange(int count, DistrictingConstraints constraints) {
        return (count >= constraints.getMinMinorityDistricts() && count<=constraints.getMaxMinorityDistricts());
    }

    public static Collection<Districting> getDistrictingsByConstraints(DistrictingConstraints constraints) {
        HashSet<Districting> filteredDistrictings = new HashSet<Districting>();
        Collection<Districting> fullJobSet = getDistrictings(constraints.getJobId());
        System.out.println("Received full job set.");
        for (Districting d : fullJobSet) {
            switch (constraints.getCompactnessType()) {
                case POLSBY_POPPER:
                    if (d.getMeasures().getCompactnessAvg().getPolsbyPopper() < constraints.getCompactnessThreshold()) {
                        continue;
                    }
                    break;
                case GRAPH_COMPACTNESS:
                    if (d.getMeasures().getCompactnessAvg().getGraphCompactness() < constraints.getCompactnessThreshold()) {
                        continue;
                    }
                    break;
                case POPULATION_FATNESS:
                    if (d.getMeasures().getCompactnessAvg().getPopulationFatness() < constraints.getCompactnessThreshold()) {
                        continue;
                    }
            }
            int majorityMinorityDistrictsCount = d.getMMDistrictsCount(constraints.getMinorityPopulation(), constraints.getMinorityThreshold());
            double populationDiff = d.getAverageDeviationFromIdeal();
            double maxPopulationDiff = constraints.getMaxPopulationDifference()/100;
            if(inMajorityMinorityDistrictRange(majorityMinorityDistrictsCount, constraints) && populationDiff <= maxPopulationDiff) {
                filteredDistrictings.add(d);
                d.getMeasures().setMajorityMinorityDistricts(majorityMinorityDistrictsCount);
            }

        }
        return filteredDistrictings;
    }
}
