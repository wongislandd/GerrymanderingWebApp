package cse416.spring.models.district;


import cse416.spring.enums.StateName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/* filePath = file in districtings folder (ex. nc_plans_1_0.json)
       indexInFile = refers to the districting this district is a part of
       districtNumber = which district it is in that districting
*/
@Entity(name = "DistrictReferences")
@Getter
@Setter
@NoArgsConstructor
public class DistrictReference {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private StateName state;
    @Column
    private String filePath;
    @Column
    private int districtingIndex;
    @Column
    private String districtKey;

    public DistrictReference(StateName state, String filePath, int districtingIndex, String districtKey) {
        this.state = state;
        this.filePath = filePath;
        this.districtingIndex = districtingIndex;
        this.districtKey = districtKey;
    }
}
