package cse416.spring.models.district;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "DistrictReferences")
@Getter
@Setter
public class DistrictReference {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String filePath;
    @Column
    private int districtingIndex;
    @Column
    private String districtKey;

    public DistrictReference(String filePath, int districtingIndex, String districtKey) {
        this.filePath = filePath;
        this.districtingIndex = districtingIndex;
        this.districtKey = districtKey;
    }

    public DistrictReference() {
    }


}
