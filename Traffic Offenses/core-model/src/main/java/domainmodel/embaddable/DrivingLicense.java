package domainmodel.embaddable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class DrivingLicense implements Serializable{

    public enum DrivingLicenseStatus{
        ACTIVE,TEMPORARY_INACTIVE, PERMAMENT_INACTIVE
    }

    @Column(unique = true)
    private String licenseNumber;
    private Date createLicenseDate;
    private DrivingLicenseStatus status;


    public DrivingLicense(){
    }

    public DrivingLicense(String licenseNumber, Date createLicenseDate, DrivingLicenseStatus status) {
        this.licenseNumber = licenseNumber;
        this.createLicenseDate = createLicenseDate;
        this.status = status;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Date getCreateLicenseDate() {
        return createLicenseDate;
    }

    public DrivingLicenseStatus getStatus() {
        return status;
    }

    public void changeStatus(DrivingLicenseStatus status) {
        this.status = status;
    }

    public boolean licenseIsActive(){
        return (status==DrivingLicenseStatus.ACTIVE);
    }



}
