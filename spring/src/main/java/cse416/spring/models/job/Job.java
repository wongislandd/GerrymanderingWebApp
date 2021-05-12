package cse416.spring.models.job;

import cse416.spring.enums.StateName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity(name="Jobs")
@Getter
@Setter
@NoArgsConstructor
public class Job {
    @Id
    private long id;
    @Column
    StateName state;
    @OneToOne(cascade = CascadeType.ALL)
    JobSummary summary;

    public Job(long id, StateName state, JobSummary summary) {
        this.id = id;
        this.state = state;
        this.summary = summary;
    }
}