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
        ),
        @NamedQuery(
                name = "User.findUserByLicenseNumber",
                query = "SELECT u FROM User u  where u.drivingLicense.licenseNumber = :licenseNumber"
        ),
        @NamedQuery(
                name = "User.findAllActiveDrivers",
                query = "SELECT u FROM User u where u.aggregateStatus = 'ACTIVE' and u.drivingLicense != null"
        ),
        @NamedQuery(
                name = "User.deleteUserRole",
                query = "SELECT u FROM User u where u.aggregateStatus = 'ACTIVE' and u.drivingLicense != null"
        ),
        @NamedQuery(
                name = "User.findAllUser",
                query = "SELECT u FROM User u"
        )
})
@Entity
public class User extends AggregateRoot{

    private String firstName;

    private String lastName;

    private String email;

    @Column(unique = true)
    private String pesel;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<UserRole> userRoles = new HashSet<UserRole>();

    @Embedded
    private DrivingLicense drivingLicense;

    public User() {
    }

    private User(UserBuilder userBuilder){
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.pesel = userBuilder.pesel;
        this.userRoles = userBuilder.userRoles;
        this.drivingLicense = userBuilder.drivingLicense;
    }

    public UserData generateUserData() {

        String drivingLicenseNumber = drivingLicense.getLicenseNumber();
        return new UserData(aggregateId, firstName, lastName, pesel, drivingLicenseNumber,email);
    }

    public void addUserRole(Role role){
       this.userRoles.add(new UserRole(role));
    }

    public UserRole deleteUserRole(Role role){
        Iterator<UserRole> iterator = this.userRoles.iterator();
        UserRole selectedUserRole = null;
        while (iterator.hasNext()) {
            UserRole element = iterator.next();
            if (element.getRole().equals(role)) {
                selectedUserRole = element;
                iterator.remove();
            }
        }
        return selectedUserRole;
    }

    public void setDrivingLicense(DrivingLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public void setUserPassword(String password){
        this.password = password;
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

    public DrivingLicense getDrivingLicense() throws NullDrivingLicenseException {
        if(drivingLicense == null)
            throw new NullDrivingLicenseException("user with id = " + this.aggregateId + "does not have drivingLicense");
        return drivingLicense;
    }

    public void changeLicenseDrivingStatus(DrivingLicense.DrivingLicenseStatus status)throws NullDrivingLicenseException {
        if(drivingLicense == null)
            throw new NullDrivingLicenseException("user with id = " + this.aggregateId + "does not have drivingLicense");
        this.drivingLicense.changeStatus(status);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class UserBuilder{

        private String firstName;
        private String lastName;
        private String email;
        private String pesel;
        private Set<UserRole> userRoles = new HashSet<UserRole>();
        private DrivingLicense drivingLicense;
        private String password;

        public UserBuilder(String firstName, String lastName, String email, String pesel) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.pesel = pesel;
            this.userRoles.add(new UserRole(Role.ROLE_PUBLIC));
        }


        public UserBuilder drivingLicense(DrivingLicense drivingLicense){
            this.drivingLicense = drivingLicense;
            if(drivingLicense!=null)
                this.password = drivingLicense.getLicenseNumber();
            return this;
        }

        public UserBuilder administratorRole(String password){
            this.password = (password);
            this.userRoles.add(new UserRole(Role.ROLE_ADMINISTRATOR));
            return this;
        }

        public UserBuilder privateRole(String password){
            this.password = (password);
            this.userRoles.add(new UserRole(Role.ROLE_PRIVATE));
            return this;
        }

        public User build(){
            User user = new User(this);
            return user;
        }

    }


}