package cse416.spring.models.districting;

import cse416.spring.enums.StateName;
import cse416.spring.models.district.District;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class EnactedDistricting extends Districting {
    private StateName state;

    public EnactedDistricting() {
    }

    public EnactedDistricting(int jobID, ArrayList<District> districts) {
        super(jobID, districts);
    }

    @Column
    public StateName getState() {
        return state;
    }

    public void setState(StateName state) {
        this.state = state;
    }
}
