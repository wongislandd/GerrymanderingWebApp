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
    private int districtIndex;

    public DistrictReference(String filePath, int districtingIndex, int districtIndex) {
        this.filePath = filePath;
        this.districtingIndex = districtingIndex;
        this.districtIndex = districtIndex;
    }

    public DistrictReference() {
    }


}
