package cse416.spring.models.district;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "MajorityMinorityInfos")
public class MajorityMinorityInfo {
    @Column
    public boolean isBlackMajority() {
        return blackMajority;
    }

    public void setBlackMajority(boolean blackMajority) {
        this.blackMajority = blackMajority;
    }

    @Column
    public boolean isHispanicMajority() {
        return hispanicMajority;
    }

    public void setHispanicMajority(boolean hispanicMajority) {
        this.hispanicMajority = hispanicMajority;
    }

    @Column
    public boolean isAsianMajority() {
        return asianMajority;
    }

    public void setAsianMajority(boolean asianMajority) {
        this.asianMajority = asianMajority;
    }

    @Column
    public boolean isNativeMajority() {
        return nativeMajority;
    }

    public void setNativeMajority(boolean nativeMajority) {
        this.nativeMajority = nativeMajority;
    }

    boolean blackMajority;
    boolean hispanicMajority;
    boolean asianMajority;
    boolean nativeMajority;
    private Long id;

    public MajorityMinorityInfo(boolean blackMajority, boolean hispanicMajority, boolean asianMajority, boolean nativeMajority) {
        this.blackMajority = blackMajority;
        this.hispanicMajority = hispanicMajority;
        this.asianMajority = asianMajority;
        this.nativeMajority = nativeMajority;
    }

    public MajorityMinorityInfo() {

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
