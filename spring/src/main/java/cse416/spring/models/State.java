package cse416.spring.models;

import cse416.spring.enums.StateName;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class State {

    @Enumerated(EnumType.STRING)
    StateName name;

    @OneToMany
    Collection<Job> jobs;

    @OneToMany
    Collection<County> counties;

    @Id
    private Long id;

    public State() {

    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
