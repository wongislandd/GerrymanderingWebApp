package cse416.spring.models.job;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.JSONObjectConverter;
import cse416.spring.models.districting.Districting;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class Job {
    private long id;
    StateName state;
    JobSummary summary;
    JSONObject districtingKeys;


    public Job(StateName state, JobSummary summary) {
        this.state = state;
        this.summary = summary;
        this.districtingKeys = new JSONObject();
    }

    public Job() {

    }

    @Column(columnDefinition = "TEXT")
    @Convert(converter = JSONObjectConverter.class)
    public JSONObject getDistrictingKeys() {
        return districtingKeys;
    }

    public void setDistrictingKeys(JSONObject geometry) {
        this.districtingKeys = geometry;
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
