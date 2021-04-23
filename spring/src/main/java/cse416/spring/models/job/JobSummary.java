package cse416.spring.models.job;

import cse416.spring.helperclasses.MGGGParams;

import javax.persistence.*;

@Entity
public class JobSummary {

    String description;
    int size;
    MGGGParams params;
    private long id;


    public JobSummary(String description, MGGGParams params, int size) {
        this.description = description;
        this.size = size;
        this.params = params;
    }

    public JobSummary() {

    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public MGGGParams getParams() {
        return params;
    }

    public void setParams(MGGGParams params) {
        this.params = params;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
