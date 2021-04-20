package cse416.spring.helperclasses.analysis;

import cse416.spring.models.Districting;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Component
public class TopScoring implements AnalysisCategoryContainer{
    ArrayList<Districting> entries;

    int maxSize = 10;

    public TopScoring(ArrayList<Districting> entries) {
        this.entries = entries;
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
                    return 1;
                } else if(difference < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    @Override
    public boolean shouldInsert(Districting districting) {
        /* If this districting's score is greater than the lowest score on the list, replace.*/
        if (entries.get(-1).getObjectiveFunctionScore() < districting.getObjectiveFunctionScore() || entries.size() < maxSize) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void insert(Districting districting) {
        if (entries.size() < maxSize) {
            entries.add(districting);
        } else {
            entries.remove(-1);
            entries.add(districting);
        }
        sortEntries();
    }
}
