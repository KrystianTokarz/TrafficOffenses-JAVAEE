package domainmodel.domain.user;


import domainmodel.embaddable.DrivingLicense;
import domainmodel.embaddable.UserData;
import domainmodel.suport.domain.AggregateRoot;
import exception.NullDrivingLicenseException;


import javax.persistence.*;
import java.util.*;

@NamedQueries({
        @NamedQuery(
                name = "User.findUserByPeselAndLicenseNumber",
                query = "SELECT u FROM User u where u.pesel = :peselID and u.drivingLicense.licenseNumber = :licenseNumber"
        ),
        @NamedQuery(
                name = "User.findUserByPesel",
                query = "SELECT u FROM User u where u.pesel = :peselID"
        )

})
@Entity
public class User extends AggregateRoot{

    private String firstName;
    private String lastName;
    private String email;

    @Column(unique = true)
    private String pesel;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<UserRole> userRoles = new HashSet<UserRole>();

//    private String role;

    @Embedded
    private DrivingLicense drivingLicense;

    private String password;


    public User() {
    }
    public User(String firstName, String lastName, String email, String pesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pesel = pesel;
        this.password=pesel;
    }


    public UserData generateUserData(){
//        String drivingLicenseNumber  = drivingLicense.getLicenseNumber();
        return new UserData(aggregateId, firstName, lastName, pesel, null);
    }
//
    public void addUserRole(UserRole role){
       this.userRoles.add(role);
    }

//    public void setUserRole(String role){
//        this.role = role;
//    }
//
//    public String getUserRole(){
//        return this.role;
//    }


    public void addDrivingLicense(DrivingLicense drivingLicense){
        this.drivingLicense = drivingLicense;
    }


    public String getPassword(){
        return password;
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

    public Collection<UserRole> getUserRoles() {
        return Collections.unmodifiableCollection(userRoles);
    }

//    public DrivingLicense getDrivingLicense(){
//        return drivingLicense;
//
  public String getLicenseNumber() throws NullDrivingLicenseException {
        if(drivingLicense == null)
            throw new NullDrivingLicenseException("user with id = " + this.aggregateId + "does not have drivingLicense");
        return drivingLicense.getLicenseNumber();
    }


    public Date getCreateLicenseDate() throws NullDrivingLicenseException {
        if(drivingLicense == null)
            throw new NullDrivingLicenseException("user with id = " + this.aggregateId + "does not have drivingLicense");
        return drivingLicense.getCreateLicenseDate();
    }

    public DrivingLicense.DrivingLicenseStatus getLicenseStatus() throws NullDrivingLicenseException {
        if(drivingLicense == null)
            throw new NullDrivingLicenseException("user with id = " + this.aggregateId + "does not have drivingLicense");
        return drivingLicense.getStatus();
    }

    public void changeDrivingLicenseStatus(DrivingLicense.DrivingLicenseStatus status) throws NullDrivingLicenseException {
        if(drivingLicense == null)
            throw new NullDrivingLicenseException("user with id = " + this.aggregateId + "does not have drivingLicense");
        this.drivingLicense.changeStatus(status);
    }

    public boolean licenseIsActive() throws NullDrivingLicenseException {
        if(drivingLicense == null)
            throw new NullDrivingLicenseException("user with id = " + this.aggregateId + "does not have drivingLicense");
        return (drivingLicense.getStatus() == DrivingLicense.DrivingLicenseStatus.ACTIVE);
    }


}