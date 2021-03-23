package cse416.spring.models;

import cse416.spring.enums.StateName;

import java.util.Collection;

public class State {
    StateName name;
    Collection<Job> jobs;
    Collection<County> counties;

    public StateName getName() {
        return name;
    }

    public void setName(StateName name) {
        this.name = name;
    }

    public Collection<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Collection<Job> jobs) {
        this.jobs = jobs;
    }

    public Collection<County> getCounties() {
        return counties;
    }

    public void setCounties(Collection<County> counties) {
        this.counties = counties;
    }

    public State(StateName name, Collection<Job> jobs, Collection<County> counties) {
        this.name = name;
        this.jobs = jobs;
        this.counties = counties;
    }
}
