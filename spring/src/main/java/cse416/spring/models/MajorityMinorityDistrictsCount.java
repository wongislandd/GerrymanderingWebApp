package cse416.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MajorityMinorityDistrictsCount {
    double blackDistricts;
    double hispanicDistricts;
    double asianDistricts;
    double nativeDistricts;
    private Long id;

    public MajorityMinorityDistrictsCount(double blackDistricts, double hispanicDistricts, double asianDistricts, double nativeDistricts) {
        this.blackDistricts = blackDistricts;
        this.hispanicDistricts = hispanicDistricts;
        this.asianDistricts = asianDistricts;
        this.nativeDistricts = nativeDistricts;
    }

    public MajorityMinorityDistrictsCount() {

    }

    @Column
    public double getBlackDistricts() {
        return blackDistricts;
    }

    public void setBlackDistricts(double blackDistricts) {
        this.blackDistricts = blackDistricts;
    }

    @Column
    public double getHispanicDistricts() {
        return hispanicDistricts;
    }

    public void setHispanicDistricts(double hispanicDistricts) {
        this.hispanicDistricts = hispanicDistricts;
    }

    @Column
    public double getAsianDistricts() {
        return asianDistricts;
    }

    public void setAsianDistricts(double asianDistricts) {
        this.asianDistricts = asianDistricts;
    }

    @Column
    public double getNativeDistricts() {
        return nativeDistricts;
    }

    public void setNativeDistricts(double nativeDistricts) {
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
