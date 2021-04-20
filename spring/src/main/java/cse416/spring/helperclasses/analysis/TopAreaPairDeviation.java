package cse416.spring.helperclasses.analysis;

import cse416.spring.models.Districting;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Component
public class TopAreaPairDeviation implements AnalysisCategoryContainer{
    ArrayList<Districting> entries;

    public TopAreaPairDeviation(ArrayList<Districting> entries) {
        this.entries = entries;
    }
    
    public ArrayList<Districting> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Districting> entries) {
        this.entries = entries;
    }

    @Override
    public boolean shouldInsert(Districting districting) {
        return false;
    }

    @Override
    public void sortEntries() {

    }

    @Override
    public void insert(Districting districting) {

    }

}
