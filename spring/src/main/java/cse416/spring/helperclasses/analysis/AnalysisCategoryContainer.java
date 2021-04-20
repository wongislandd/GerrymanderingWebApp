package cse416.spring.helperclasses.analysis;

import cse416.spring.models.Districting;

public interface AnalysisCategoryContainer {

    boolean shouldInsert(Districting districting);

    void sortEntries();

    void insert(Districting districting);


}
