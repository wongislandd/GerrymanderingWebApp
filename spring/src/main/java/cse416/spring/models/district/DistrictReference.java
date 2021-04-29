package cse416.spring.models.district;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "DistrictReferences")
public class DistrictReference {
    private long id;
    private String filePath;
    private int districtingIndex;
    private int districtIndex;

    public DistrictReference(String filePath, int districtingIndex, int districtIndex) {
        this.filePath = filePath;
        this.districtingIndex = districtingIndex;
        this.districtIndex = districtIndex;
    }

    public DistrictReference() {}

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Column
    public int getDistrictingIndex() {
        return districtingIndex;
    }

    public void setDistrictingIndex(int districtingIndex) {
        this.districtingIndex = districtingIndex;
    }

    @Column
    public int getDistrictIndex() {
        return districtIndex;
    }

    public void setDistrictIndex(int districtIndex) {
        this.districtIndex = districtIndex;
    }
}
