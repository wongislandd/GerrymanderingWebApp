package cse416.spring.models.state;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.models.county.County;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.job.Job;
import cse416.spring.models.precinct.Incumbent;
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
    @Column
    private String outline;

    public State(StateName name, Collection<Job> jobs) {
        // TODO calculate outline from union of precincts matching statename
        this.name = name;
        this.jobs = jobs;
    }

}
