package cse416.spring.helperclasses;

import cse416.spring.models.Districting;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TopScoring {
    ArrayList<Districting> entries;

    public TopScoring(ArrayList<Districting> entries) {
        this.entries = entries;
    }

    public ArrayList<Districting> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Districting> entries) {
        this.entries = entries;
    }
}
