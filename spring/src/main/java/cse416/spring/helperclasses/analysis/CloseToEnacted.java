package cse416.spring.helperclasses.analysis;

import cse416.spring.models.districting.Districting;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class CloseToEnacted implements AnalysisCategoryContainer{
    ArrayList<Districting> entries;

    public CloseToEnacted() {
        this.entries = new ArrayList<>();
    }

    public ArrayList<Districting> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Districting> entries) {
        this.entries = entries;
    }

    @Override
    public boolean shouldInsert(Districting districting) {
        // sort similar to top scoring based on deviation to enacted instead
        return true;
    }

    @Override
    public void sortEntries() {

    }


    @Override
    public void insert(Districting districting) {

    }

}
