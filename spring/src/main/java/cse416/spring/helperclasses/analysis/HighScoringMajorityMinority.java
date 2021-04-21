package cse416.spring.helperclasses.analysis;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.districting.Districting;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class HighScoringMajorityMinority implements AnalysisCategoryContainer{
    ArrayList<Districting> entries;
    MinorityPopulation minority;
    int desiredMinorityDistrictUpperBound;
    int desiredMinorityDistrictLowerBound;

    public HighScoringMajorityMinority(ArrayList<Districting> entries, MinorityPopulation minority, int desiredMinorityDistrictUpperBound, int desiredMinorityDistrictLowerBound) {
        this.entries = entries;
        this.minority = minority;
        this.desiredMinorityDistrictUpperBound = desiredMinorityDistrictUpperBound;
        this.desiredMinorityDistrictLowerBound = desiredMinorityDistrictLowerBound;
    }

    public ArrayList<Districting> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Districting> entries) {
        this.entries = entries;
    }

    @Override
    public boolean shouldInsert(Districting districting) {
        switch(minority) {
            case BLACK:
                int blackDistricts = districting.getMeasures().getMinorityDistrictsCount().getBlackDistricts();
                if (desiredMinorityDistrictLowerBound <= blackDistricts &&
                        blackDistricts <= desiredMinorityDistrictUpperBound) {
//                        districting.getObjectiveFunctionScore();
//                        districting.getMeasures().getDeviationFromAverageAvg();
                }
        }

        return false;
    }

    @Override
    public void sortEntries() {

    }


    @Override
    public void insert(Districting districting) {

    }

}
