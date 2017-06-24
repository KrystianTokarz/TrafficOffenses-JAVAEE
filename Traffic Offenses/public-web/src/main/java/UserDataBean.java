
import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.PublicUserDataFinder;
import readmodel.SimpleUserDataVO;
import domainmodel.embaddable.DrivingLicense;
import exception.NullDrivingLicenseException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@ManagedBean
@SessionScoped
public class UserDataBean implements Serializable {

    @EJB
    private PublicUserDataFinder publicUserFinder;

    @ManagedProperty(value="#{authenticationBean}")
    private AuthenticationBean authenticationBean;


    private SimpleUserDataVO userData;

    private DrivingLicense drivingLicense;

    private boolean userHasDrivingLicense = true;



    @PostConstruct
    public void init() throws UserNotFoundException, UserNotActiveException {
        String pesel = authenticationBean.getUserPesel();
        userData = publicUserFinder.findSimpleUserData(pesel);
        checkUserDrivingLicense();
    }

    private void checkUserDrivingLicense(){
        try {
            drivingLicense = userData.getUser().getDrivingLicense();
        } catch (NullDrivingLicenseException e) {
            userHasDrivingLicense = false;
        }
    }
    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public boolean isUserHasDrivingLicense() {
        return userHasDrivingLicense;
    }

    public void setUserHasDrivingLicense(boolean userHasDrivingLicense) {
        this.userHasDrivingLicense = userHasDrivingLicense;
    }

    public String getFirstName(){
        return userData.getUser().getFirstName();
    }

    public String getLastName(){
        return userData.getUser().getLastName();
    }

    public String getPesel(){
        return userData.getUser().getPesel();
    }
    public String getEmail(){
        return userData.getUser().getEmail();
    }
    public String getLicenseNumber(){
        return drivingLicense.getLicenseNumber();
    }
    public DrivingLicense.DrivingLicenseStatus getLicenseStatus(){
            return drivingLicense.getStatus();
    }

    public Date getCreationLicenseDate(){
       return drivingLicense.getCreateLicenseDate();
    }

}
