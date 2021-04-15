package cse416.spring.models;

import javax.persistence.*;

@Entity
public class Demographics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @Column
    int democrats;

    @Column
    int republicans;

    @Column
    int otherParty;

    @Column
    int asian;

    @Column
    int black;

    @Column
    int natives;

    @Column
    int white;

    @Column
    int otherRace;

    @Column
    int TVP;

    @Column
    int VAP;

    @Column
    int CVAP;
    public Demographics() {

    }


}
