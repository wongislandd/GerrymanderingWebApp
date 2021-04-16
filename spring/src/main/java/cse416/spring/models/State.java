package cse416.spring.models;

import cse416.spring.enums.StateName;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class State {
    StateName name;
    Collection<Job> jobs;
    Collection<County> counties;
    private Long id;

    public State() {

    }

    public State(StateName name, Collection<Job> jobs, Collection<County> counties) {
        this.name = name;
        this.jobs = jobs;
        this.counties = counties;
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

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
