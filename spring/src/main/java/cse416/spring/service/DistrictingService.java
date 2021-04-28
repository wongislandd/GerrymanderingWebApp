package cse416.spring.service;


import cse416.spring.models.districting.Districting;

import java.util.List;

public interface DistrictingService {
    Districting findById(long id);

    List<Districting> findByJob(int jobId);

    List<Districting> findAllDistrictings();

//    public static void getInterestingDistrictings() {
//        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
//        em.getTransaction().begin();
//        Query query = em.createQuery("SELECT d FROM Districting d");
//        ArrayList<Districting> allDistrictings = new ArrayList<Districting>(query.getResultList());
//        // ^ ITERATE THROUGH THIS LIST OF DISTRICTINGS AND GET THE 4 CATEGORIES
//        CloseToEnacted closeToEnacted = new CloseToEnacted();
//        HighScoringMajorityMinority highScoringMajorityMinority = new HighScoringMajorityMinority(MinorityPopulation.BLACK, 3, 6);
//        TopAreaPairDeviation topAreaPairDeviation = new TopAreaPairDeviation();
//        TopScoring topScoring = new TopScoring();
//        for (int i=0;i<allDistrictings.size();i++) {
//            Districting currentDistricting = allDistrictings.get(i);
//            if (closeToEnacted.shouldInsert(currentDistricting)) {
//                closeToEnacted.insert(currentDistricting);
//            }
//            if (highScoringMajorityMinority.shouldInsert(currentDistricting)) {
//                highScoringMajorityMinority.insert(currentDistricting);
//            }
//            if (topAreaPairDeviation.shouldInsert(currentDistricting)) {
//                topAreaPairDeviation.insert(currentDistricting);
//            }
//            if (topScoring.shouldInsert(currentDistricting)) {
//                topScoring.insert(currentDistricting);
//            }
//        }
//        // RESULTS
//        ArrayList<Districting> r1 = closeToEnacted.getEntries();
//        ArrayList<Districting> r2 = highScoringMajorityMinority.getEntries();
//        ArrayList<Districting> r3 = topAreaPairDeviation.getEntries();
//        ArrayList<Districting> r4 = topScoring.getEntries();
//        System.out.println("Put breakpoint here to view the listings after");
//    }
}
