package cse416.spring.helperclasses.analysis;

import cse416.spring.helperclasses.DistrictingSummary;
import cse416.spring.models.districting.Districting;

public interface AnalysisCategoryContainer {

    boolean shouldInsert(DistrictingSummary summary);

    void insert(DistrictingSummary summary);

    void sortEntries();

    void insertIfFit(DistrictingSummary summary);
}
