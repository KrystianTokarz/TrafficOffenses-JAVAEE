package domainmodel.embaddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class UserData implements Serializable {

    @Column
    private Long userAggregateRootId;
    private String firstName;
    private String lastName;
    private String pesel;
    private String licenseNumber;

    public UserData(){

    }

    public UserData(Long userAggregateRootId, String firstName, String lastName, String pesel, String licenseNumber) {
        this.userAggregateRootId = userAggregateRootId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.licenseNumber = licenseNumber;
    }

    public Long getUserAggregateRootId() {
        return userAggregateRootId;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getPesel() {
        return pesel;
    }



    public String getLicenseNumber() {
        return licenseNumber;
    }


    //
//    public Long getUserAggregateRootId() {
//        return this.userAggregateRootId;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getPesel() {
//        return pesel;
//    }
//
//    public String getLicenseNumber() {
//        return licenseNumber;
//    }
}
