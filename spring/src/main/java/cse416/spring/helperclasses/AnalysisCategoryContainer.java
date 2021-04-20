package cse416.spring.helperclasses;

import cse416.spring.models.Districting;

public interface AnalysisCategoryContainer {

    boolean shouldInsert(Districting districting);

    Districting findDistrictingToBeReplacedBy(Districting districting);

    void remove(Districting districting);

    void insert(Districting districting);

    boolean inertIfFits(Districting districting);

}
