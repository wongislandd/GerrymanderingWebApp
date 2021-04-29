package cse416.spring.models.districting;


import cse416.spring.enums.StateName;
import cse416.spring.models.district.District;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "EnactedDistrictings")
public class EnactedDistricting {
    private long id;
    private StateName state;
    private Collection<District> districts;

    public EnactedDistricting(StateName state, Collection<District> districts) {
        this.state = state;
        this.districts = districts;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public StateName getState() {
        return state;
    }

    public void setState(StateName state) {
        this.state = state;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Collection<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Collection<District> districts) {
        this.districts = districts;
    }
}
