package cse416.spring.models.state;

import cse416.spring.enums.StateName;
import cse416.spring.models.job.Job;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;


@Entity(name = "States")
@Getter
@Setter
@NoArgsConstructor
public class State {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private StateName name;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Job> jobs;
    @Transient
    private Job currentJob;
    // TODO do we need this?
    @Column
    private String outline;

    public State(StateName name, Collection<Job> jobs) {
        this.name = name;
        this.jobs = jobs;
    }
}
