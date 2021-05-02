package cse416.spring.database;

import cse416.spring.enums.StateName;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictReference;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.EnactedDistricting;
import cse416.spring.models.job.Job;
import cse416.spring.models.precinct.Precinct;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import static cse416.spring.database.DistrictingWriter.getPrecinctsFromKeys;

@AllArgsConstructor
public class DistrictingWriterThread extends Thread {
    StateName stateName;
    Job job;
    String name;
    EntityManager em;
    EnactedDistricting enactedDistricting;
    JSONArray districtings;
    String filePath;
    HashMap<Integer, Precinct> precinctHash;
    int rangeStart;
    int rangeEndExclusive;
    AtomicBoolean availableRef;

    @Override
    public synchronized void start() {
        super.start();
    }


    private void commit() {
        boolean first = true;

        while (true) {
            if (availableRef.compareAndSet(true, false)) {
                System.out.println("[THREAD " + name + "] Starting commit");
                final long startTime = System.currentTimeMillis();
                em.getTransaction().commit();
                final long endTime = System.currentTimeMillis();
                System.out.println("[THREAD " + name + "] Committed in: " + (endTime - startTime) + "ms");
                availableRef.set(true);
                break;
            } else {
                if (first) {
                    first = false;
                }
            }
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        em.getTransaction().begin();

        /* For each districting */
        for (int i = rangeStart; i < rangeEndExclusive; i++) {
            JSONObject districting = districtings.getJSONObject(i);
            Iterator<String> keys = districting.keys();
            ArrayList<District> districtsInDistricting = new ArrayList<>();

            /* For each district in the districting */
            while (keys.hasNext()) {
                String districtKey = keys.next();
                JSONArray precinctKeysInDistrict = districting.getJSONArray(districtKey);
                Collection<Precinct> precincts = getPrecinctsFromKeys(precinctKeysInDistrict, precinctHash);

                DistrictReference districtReference = new DistrictReference(stateName, filePath, i, districtKey);
                // TODO: Change the null to the enacted districting
                District d = new District(precincts, stateName, null, districtReference);
                districtsInDistricting.add(d);
                em.persist(d);
                System.out.println("[THREAD " + name + "] Created District.");
            }

            Districting newDistricting = new Districting(job, districtsInDistricting);
            // TODO FIX THIS
            newDistricting.renumberDistricts(enactedDistricting);
            em.persist(newDistricting);
        }
        commit();
    }
}
