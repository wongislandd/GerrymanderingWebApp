package cse416.spring.models.precinct;

import cse416.spring.enums.StateName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;



@Entity(name = "Incumbents")
@Getter
@Setter
@NoArgsConstructor
public class Incumbent {
    @Column
    String name;
    @Column
    String residence;
    @Id
    @GeneratedValue
    private long id;
    @Column
    StateName state;

    public Incumbent(String name, StateName state, String residence) {
        this.name = name;
        this.state = state;
        this.residence = residence;
    }
    
}
