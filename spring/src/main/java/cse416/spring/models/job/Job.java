package cse416.spring.models.job;

import cse416.spring.enums.StateName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.*;


@Entity(name="Jobs")
@Getter
@Setter
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue
    private long id;
    @Column
    StateName state;
    @OneToOne(cascade = CascadeType.ALL)
    JobSummary summary;

    public Job(StateName state, JobSummary summary) {
        this.state = state;
        this.summary = summary;
    }
}