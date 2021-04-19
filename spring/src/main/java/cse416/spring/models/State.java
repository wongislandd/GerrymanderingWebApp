package cse416.spring.models;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.FeatureCollectionJSON;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class State {
    StateName name;
    Collection<Job> jobs;
    Job currentJob;
    FeatureCollectionJSON outline;
    Collection<County> counties;
    Districting enactedDistricting;
    Collection<Incumbent> incumbents;
    private long id;

    public State() {

    }

    public State(StateName name, Collection<Job> jobs, Job currentJob, FeatureCollectionJSON outline, Collection<County> counties, Districting enactedDistricting, Collection<Incumbent> incumbents) {
        this.name = name;
        this.jobs = jobs;
        this.currentJob = currentJob;
        this.outline = outline;
        this.counties = counties;
        this.enactedDistricting = enactedDistricting;
        this.incumbents = incumbents;
    }

    @Enumerated(EnumType.STRING)
    public StateName getName() {
        return name;
    }

    public void setName(StateName name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Collection<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Collection<Job> jobs) {
        this.jobs = jobs;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Collection<County> getCounties() {
        return counties;
    }

    public void setCounties(Collection<County> counties) {
        this.counties = counties;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Job getCurrentJob() {
        return this.currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public FeatureCollectionJSON getOutline() {
        return this.outline;
    }

    public void setOutline(FeatureCollectionJSON outline) {
        this.outline = outline;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Districting getEnactedDistricting() {
        return this.enactedDistricting;
    }

    public void setEnactedDistricting(Districting enactedDistricting) {
        this.enactedDistricting = enactedDistricting;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Collection<Incumbent> getIncumbents() {
        return this.incumbents;
    }

    public void setIncumbents(Collection<Incumbent> incumbents) {
        this.incumbents = incumbents;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
