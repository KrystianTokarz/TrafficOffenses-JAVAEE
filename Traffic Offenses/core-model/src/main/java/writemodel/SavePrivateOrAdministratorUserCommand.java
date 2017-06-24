package writemodel;

import domainmodel.domain.user.Role;
import domainmodel.embaddable.DrivingLicense;

import java.util.Date;

public class SavePrivateOrAdministratorUserCommand implements Command {

    private String firstName;
    private String lastName;
    private String email;
    private String pesel;
    private String password;
    private boolean hasDrivingLicenseNumber = false;
    private String drivingLicenseNumber;
    private Date drivingLicenseCreationDate;
    private DrivingLicense.DrivingLicenseStatus drivingLicenseStatus;
    private Role role;


    public SavePrivateOrAdministratorUserCommand(String firstName, String lastName, String email, String pesel, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pesel = pesel;
        this.password = password;
        this.role = role;
    }

    public SavePrivateOrAdministratorUserCommand(String firstName, String lastName, String email, String pesel, String password, Role role, String drivingLicenseNumber, Date drivingLicenseCreationDate, DrivingLicense.DrivingLicenseStatus drivingLicenseStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pesel = pesel;
        this.password = password;
        this.role = role;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.drivingLicenseCreationDate = drivingLicenseCreationDate;
        this.drivingLicenseStatus = drivingLicenseStatus;
        this.hasDrivingLicenseNumber = true;
    }

    public String getPassword() {
        return password;
    }

    public boolean isHasDrivingLicenseNumber() {
        return hasDrivingLicenseNumber;
    }

    public Date getDrivingLicenseCreationDate() {
        return drivingLicenseCreationDate;
    }

    public DrivingLicense.DrivingLicenseStatus getDrivingLicenseStatus() {
        return drivingLicenseStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPesel() {
        return pesel;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public Role getRole() {
        return role;
    }
}
