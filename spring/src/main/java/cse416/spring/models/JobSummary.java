package cse416.spring.models;

import cse416.spring.helperclasses.MGGGParams;

import javax.persistence.*;

@Entity
public class JobSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    int id;

    public JobSummary(int id, String description, int size, MGGGParams params) {
        this.id = id;
        this.description = description;
        this.size = size;
        this.params = params;
    }

    @Column
    String description;

    @Column
    int size;

    @OneToOne
    MGGGParams params;

    @OneToOne
    Job j;

    public JobSummary() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MGGGParams getParams() {
        return params;
    }

    public void setParams(MGGGParams params) {
        this.params = params;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
