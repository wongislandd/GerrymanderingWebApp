package cse416.spring.models.job;

import cse416.spring.enums.StateName;
import org.json.JSONObject;

import javax.persistence.*;


@Entity(name="Jobs")
public class Job {
    private long id;
    StateName state;
    JobSummary summary;

    public Job(StateName state, JobSummary summary) {
        this.state = state;
        this.summary = summary;
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

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public JobSummary getSummary() {
        return summary;
    }

    public void setSummary(JobSummary summary) {
        this.summary = summary;
    }


}
