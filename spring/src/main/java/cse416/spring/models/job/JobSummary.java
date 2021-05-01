package cse416.spring.models.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "JobSummaries")
@Getter
@Setter
@NoArgsConstructor
public class JobSummary {
    @Column
    String description;
    @Column
    int size;
    @OneToOne(cascade = CascadeType.ALL)
    MGGGParams params;
    @Id
    @GeneratedValue
    private long id;


    public JobSummary(String description, MGGGParams params, int size) {
        this.description = description;
        this.size = size;
        this.params = params;
    }

}
