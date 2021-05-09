package cse416.spring.helperclasses.analysis;

import cse416.spring.helperclasses.DistrictingSummary;
import cse416.spring.models.districting.Districting;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Getter
@Setter
public class AreaPairDeviationHighlight implements AnalysisCategoryContainer{
    ArrayList<DistrictingSummary> entries;

    public AreaPairDeviationHighlight() {
        this.entries = new ArrayList<>();
    }

    @Override
    public boolean shouldInsert(DistrictingSummary summary) {

        return true;
    }

    @Override
    public void sortEntries() {

    }

    @Override
    public void insertIfFit(DistrictingSummary summary) {

    }

    @Override
    public void insert(DistrictingSummary summary) {

    }



}
