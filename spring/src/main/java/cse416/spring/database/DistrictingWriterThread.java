package cse416.spring.database;

import cse416.spring.enums.StateName;
import cse416.spring.models.district.Deviation;
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
import org.locationtech.jts.util.Stopwatch;

import javax.persistence.EntityManager;
import java.util.*;
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
                //System.out.println("[THREAD " + name + "] Starting commit");
                final long startTime = System.currentTimeMillis();
                em.getTransaction().commit();
                final long endTime = System.currentTimeMillis();
                //System.out.println("[THREAD " + name + "] Committed in: " + (endTime - startTime) + "ms");
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
            final long fileStartTime = System.currentTimeMillis();
            JSONObject districting = districtings.getJSONObject(i);
            Iterator<String> keys = districting.keys();
            ArrayList<District> districtsInDistricting = new ArrayList<>();

            /* For each district in the districting */
            while (keys.hasNext()) {
                String districtKey = keys.next();
                JSONArray precinctKeysInDistrict = districting.getJSONArray(districtKey);
                Collection<Precinct> precincts = getPrecinctsFromKeys(precinctKeysInDistrict, precinctHash);

                DistrictReference districtReference = new DistrictReference(stateName, filePath, i, districtKey);
                // We'll never be able to compare it until after all districts in the districting are in
                District d = new District(precincts, stateName, districtReference);
                districtsInDistricting.add(d);
                em.persist(d);
            }

            Districting newDistricting = new Districting(job, districtsInDistricting);
            newDistricting.renumberDistricts(enactedDistricting);
            HashSet<Integer> districtNumbersSeen = new HashSet<>();
            for (District d : newDistricting.getDistricts()) {
                if (districtNumbersSeen.contains(d.getDistrictNumber())) {
                    System.out.println("[FIRST] DUPLICATE NUMBER " + d.getDistrictNumber());
                } else {
                    districtNumbersSeen.add(d.getDistrictNumber());
                }
            }


            Deviation totalDeviationFromEnacted = new Deviation();
            /* Assign deviation from enacted, now that the districts line up */
            for (District d : newDistricting.getDistricts()) {
                District correspondingDistrict = enactedDistricting.getDistrictByNumber(d.getDistrictNumber());
                Deviation devFromEnacted = d.calculateDeviationFrom(correspondingDistrict);
                d.getMeasures().setDeviationFromEnacted(devFromEnacted);
                totalDeviationFromEnacted.addAbsolute(devFromEnacted);
            }
            newDistricting.getMeasures().setDeviationFromEnactedAvg(totalDeviationFromEnacted.getAverage(newDistricting.getDistricts().size()));
            em.persist(newDistricting);
            final long fileEndTime = System.currentTimeMillis();
            //System.out.println("[THREAD " + name + "] Created Districting in " + (fileEndTime - fileStartTime) + "ms");
        }
        commit();
    }
}
