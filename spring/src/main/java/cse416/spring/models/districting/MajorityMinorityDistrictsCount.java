package cse416.spring.models.districting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MajorityMinorityDistrictsCount {
    int blackDistricts;
    int hispanicDistricts;
    int asianDistricts;
    int nativeDistricts;
    private Long id;

    public MajorityMinorityDistrictsCount(int blackDistricts, int hispanicDistricts, int asianDistricts, int nativeDistricts) {
        this.blackDistricts = blackDistricts;
        this.hispanicDistricts = hispanicDistricts;
        this.asianDistricts = asianDistricts;
        this.nativeDistricts = nativeDistricts;
    }

    public MajorityMinorityDistrictsCount() {

    }

    @Column
    public int getBlackDistricts() {
        return blackDistricts;
    }

    public void setBlackDistricts(int blackDistricts) {
        this.blackDistricts = blackDistricts;
    }

    @Column
    public int getHispanicDistricts() {
        return hispanicDistricts;
    }

    public void setHispanicDistricts(int hispanicDistricts) {
        this.hispanicDistricts = hispanicDistricts;
    }

    @Column
    public int getAsianDistricts() {
        return asianDistricts;
    }

    public void setAsianDistricts(int asianDistricts) {
        this.asianDistricts = asianDistricts;
    }

    @Column
    public int getNativeDistricts() {
        return nativeDistricts;
    }

    public void setNativeDistricts(int nativeDistricts) {
        this.nativeDistricts = nativeDistricts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }
}
