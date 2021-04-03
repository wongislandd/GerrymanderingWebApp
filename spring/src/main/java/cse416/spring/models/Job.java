package cse416.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;


@Entity
public class Job {
    @Column
    String name;
    @Id
    private Long id;

    public Job() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Districting> getDistrictings() {
        return districtings;
    }

    public void setDistrictings(Collection<Districting> districtings) {
        this.districtings = districtings;
    }

    public Job(String name, Collection<Districting> districtings) {
        this.name = name;
        this.districtings = districtings;
    }

    @OneToMany
    Collection<Districting> districtings;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
