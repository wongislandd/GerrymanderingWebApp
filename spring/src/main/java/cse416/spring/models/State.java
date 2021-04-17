package cse416.spring.models;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.GeometryJSON;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class State {
    StateName name;
    Collection<Job> jobs;
    Job currentJob;
    GeometryJSON outline;
    Collection<County> counties;
    Districting enactedDistricting;
    Collection<Incumbent> incumbents;
    private Long id;

    public State() {

    }

    public State(StateName name, Collection<Job> jobs, Job currentJob, GeometryJSON outline, Collection<County> counties, Districting enactedDistricting, Collection<Incumbent> incumbents) {
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

    @OneToMany
    public Collection<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Collection<Job> jobs) {
        this.jobs = jobs;
    }

    @OneToMany
    public Collection<County> getCounties() {
        return counties;
    }

    public void setCounties(Collection<County> counties) {
        this.counties = counties;
    }

    @OneToOne
    public Job getCurrentJob() {
        return this.currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    @OneToOne
    public GeometryJSON getOutline() {
        return this.outline;
    }

    public void setOutline(GeometryJSON outline) {
        this.outline = outline;
    }

    @OneToOne
    public Districting getEnactedDistricting() {
        return this.enactedDistricting;
    }

    public void setEnactedDistricting(Districting enactedDistricting) {
        this.enactedDistricting = enactedDistricting;
    }

    @OneToMany
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
