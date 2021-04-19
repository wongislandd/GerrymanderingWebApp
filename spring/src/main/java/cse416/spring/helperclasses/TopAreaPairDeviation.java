package cse416.spring.helperclasses;

import cse416.spring.models.Districting;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;

@Component
public class TopAreaPairDeviation {
    ArrayList<Districting> entries;

    public TopAreaPairDeviation(ArrayList<Districting> entries) {
        this.entries = entries;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public ArrayList<Districting> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Districting> entries) {
        this.entries = entries;
    }
}
