package cse416.spring.models.precinct;

import cse416.spring.enums.StateName;

import javax.persistence.*;



@Entity
public class Incumbent {
    String name;
    String residence;
    private long id;
    StateName state;

    public Incumbent(){

    }

    public Incumbent(String name, StateName state, String residence) {
        this.name = name;
        this.state = state;
        this.residence = residence;
    }

    @Column
    public StateName getState() {
        return state;
    }

    public void setState(StateName state) {
        this.state = state;
    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getResidence() {
        return this.residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }
    
    @Id
    @GeneratedValue
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}
