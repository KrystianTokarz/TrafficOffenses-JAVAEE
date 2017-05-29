package writemodel;

import java.util.Date;

public class SavePublicUserCommand implements Command {

    private String firstName;
    private String lastName;
    private String email;
    private String pesel;
    private String drivingLicenseNumber;
    private Date drivingLicenseCreationDate;
    private String drivingLicenseStatus;

    public SavePublicUserCommand() {
    }

    public SavePublicUserCommand(String firstName, String lastName, String email, String pesel, String drivingLicenseNumber, Date drivingLicenseCreationDate, String drivingLicenseStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pesel = pesel;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.drivingLicenseCreationDate = drivingLicenseCreationDate;
        this.drivingLicenseStatus = drivingLicenseStatus;
    }

    public Date getDrivingLicenseCreationDate() {
        return drivingLicenseCreationDate;
    }

    public void setDrivingLicenseCreationDate(Date drivingLicenseCreationDate) {
        this.drivingLicenseCreationDate = drivingLicenseCreationDate;
    }

    public String getDrivingLicenseStatus() {
        return drivingLicenseStatus;
    }

    public void setDrivingLicenseStatus(String drivingLicenseStatus) {
        this.drivingLicenseStatus = drivingLicenseStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }
}
