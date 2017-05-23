package infrastructure.creation.domainmodel.user;


import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;

import javax.enterprise.context.Dependent;

@Dependent
public class DefaultUserBuilder {


    private User user;


    public DefaultUserBuilder() {
//        user = new User();
    }
//
    public DefaultUserBuilder(String firstName, String lastName, String email, String pesel){
        user = new User(firstName, lastName, email, pesel);
    }


    public DefaultUserBuilder drivingLicese(DrivingLicense drivingLicense){
        user.addDrivingLicense(drivingLicense);
        return this;
    }



    public User build(){

        return this.user;
    }

}
