package cse416.spring.service;

import cse416.spring.helperclasses.EntityManagerSingleton;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;

public class DistrictingService {
    public static void getInterestingDistrictings() {
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT d FROM Districting d");
        ArrayList<Districting> allDistrictings = new ArrayList<Districting>(query.getResultList());
        // ^ ITERATE THROUGH THIS LIST OF DISTRICTINGS AND GET THE 4 CATEGORIES

    }
}
