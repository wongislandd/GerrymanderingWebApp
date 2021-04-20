package cse416.spring.helperclasses.analysis;

import cse416.spring.models.districting.Districting;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CloseToEnacted implements AnalysisCategoryContainer{
    ArrayList<Districting> entries;

    public CloseToEnacted(ArrayList<Districting> entries) {
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
