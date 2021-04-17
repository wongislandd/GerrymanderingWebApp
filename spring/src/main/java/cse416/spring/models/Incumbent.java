package cse416.spring.models;

import javax.persistence.*;



@Entity
public class Incumbent {
    String name;
    String residence;
    private long id;

    public Incumbent(){

    }

    public Incumbent(String name, String residence) {
        this.name = name;
        this.residence = residence;
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
