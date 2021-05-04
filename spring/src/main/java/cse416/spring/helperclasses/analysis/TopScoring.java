package cse416.spring.helperclasses.analysis;

import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.DistrictingMeasures;

import java.util.ArrayList;
import java.util.Comparator;

public class TopScoring implements AnalysisCategoryContainer{
    ArrayList<Districting> entries;

    int maxSize = 10;

    public TopScoring() {
        this.entries = new ArrayList<>();
    }

    public ArrayList<Districting> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Districting> entries) {
        this.entries = entries;
    }


    public void sortEntries() {
        entries.sort(new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                double difference = d1.getObjectiveFunctionScore() - d2.getObjectiveFunctionScore();
                if (difference > 0) {
                    return -1;
                } else if(difference < 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    // TODO Remove after testing
    public void forceInsert(Districting d) {
        entries.add(d);
    }

    @Override
    public boolean shouldInsert(Districting districting) {
        /* If this districting's score is greater than the lowest score on the list, replace.*/
        if (entries.size() < maxSize || entries.get(entries.size() - 1).getObjectiveFunctionScore() < districting.getObjectiveFunctionScore()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void insert(Districting districting) {
        if (entries.size() < maxSize) {
            entries.add(districting);
        }
        else {
            entries.remove(entries.size() - 1);
            entries.add(districting);
        }
        sortEntries();
    }
}
