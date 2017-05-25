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

    private String password;
    @OneToMany(cascade = CascadeType.ALL)
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
//    public User(String firstName, String lastName, String email, String pesel) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.pesel = pesel;
//    }


    public UserData generateUserData() throws NullDrivingLicenseException {
        if(drivingLicense == null)
            throw new NullDrivingLicenseException("user with id = " + this.aggregateId + "does not have drivingLicense");
        String drivingLicenseNumber  = drivingLicense.getLicenseNumber();
        return new UserData(aggregateId, firstName, lastName, pesel, drivingLicenseNumber);
    }

    public void addUserRole(Role role){
       this.userRoles.add(new UserRole(role.toString()));
    }


    public void addDrivingLicense(DrivingLicense drivingLicense){
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
            this.userRoles.add(new UserRole(Role.ROLE_PUBLIC.toString()));
            this.password = pesel;

            }

//        public UserBuilder password(String password){
//            this.password = (password);
//            return this;
//        }

        public UserBuilder drivingLicense(DrivingLicense drivingLicense){
            this.drivingLicense = drivingLicense;
            return this;
        }

        public UserBuilder administratorRole(String password){
            this.password = (password);
            this.userRoles.add(new UserRole(Role.ROLE_PRIVATE.toString()));
            return this;
        }

        public UserBuilder privateRole(String password){
            this.password = (password);
            this.userRoles.add(new UserRole(Role.ROLE_PRIVATE.toString()));
            return this;
        }

        public User build(){
            User user = new User(this);
            return user;
        }

    }


}