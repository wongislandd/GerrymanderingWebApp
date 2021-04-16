package cse416.spring.models;

import cse416.spring.enums.StateName;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class Job {
    private long id;
    StateName state;
    JobSummary summary;
    Collection<Districting> districtings;

    public Job(StateName state, JobSummary summary, Collection<Districting> districtings) {
        this.state = state;
        this.id = id;
        this.summary = summary;
        this.districtings = districtings;
    }

    public Job() {

    }

    @Column
    public StateName getState() {
        return state;
    }

    public void setState(StateName state) {
        this.state = state;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    public JobSummary getSummary() {
        return summary;
    }

    public void setSummary(JobSummary summary) {
        this.summary = summary;
    }
    @OneToMany
    public Collection<Districting> getDistrictings() {
        return districtings;
    }

    public void setDistrictings(Collection<Districting> districtings) {
        this.districtings = districtings;
    }


}
