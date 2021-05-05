package cse416.spring.helperclasses.analysis;

import cse416.spring.models.districting.Districting;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Getter
@Setter
public class TopAreaPairDeviation implements AnalysisCategoryContainer{
    ArrayList<Districting> entries;

    public TopAreaPairDeviation() {
        this.entries = new ArrayList<>();
    }

    @Override
    public boolean shouldInsert(Districting districting) {

        return true;
    }

    @Override
    public void sortEntries() {

    }

    @Override
    public void insert(Districting districting) {

    }



}
