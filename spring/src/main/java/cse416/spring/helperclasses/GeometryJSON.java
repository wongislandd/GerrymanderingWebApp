package cse416.spring.helperclasses;

import javax.persistence.*;

@Entity
public class GeometryJSON {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;
}
