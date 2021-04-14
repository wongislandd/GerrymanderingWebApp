package cse416.spring.models;

import cse416.spring.enums.StateName;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class Job {
    public StateName getState() {
        return state;
    }

    public void setState(StateName state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JobSummary getSummary() {
        return summary;
    }

    public void setSummary(JobSummary summary) {
        this.summary = summary;
    }

    public Collection<Districting> getDistrictings() {
        return districtings;
    }

    public void setDistrictings(Collection<Districting> districtings) {
        this.districtings = districtings;
    }

    @Column
    StateName state;

    public Job(StateName state, int id, JobSummary summary, Collection<Districting> districtings) {
        this.state = state;
        this.id = id;
        this.summary = summary;
        this.districtings = districtings;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    JobSummary summary;


    @OneToMany
    Collection<Districting> districtings;

    public Job() {

    }


}
