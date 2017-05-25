
import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.readmodel.PublicUserDataFinder;
import readmodel.SimpleUserDataVO;
import domainmodel.embaddable.DrivingLicense;
import exception.NullDrivingLicenseException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Date;

@ManagedBean(name = "userData")
@SessionScoped
public class UserDataBean {

    @EJB
    private PublicUserDataFinder publicUserFinder;

    private SimpleUserDataVO userData;

    @PostConstruct
    public void init(){
        String pesel = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            try {
                userData = publicUserFinder.findSimpleUserData(pesel);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            } catch (UserNotActiveException e) {
                e.printStackTrace();
            }
    }


    public String getFirstName(){
        if(userData== null)
            return "pyk";
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
        try {
            return userData.getUser().getDrivingLicense().getLicenseNumber();
        } catch (NullDrivingLicenseException e) {
            return "brak informacji";
        }
    }
    public DrivingLicense.DrivingLicenseStatus getLicenseStatus(){
        try {
            return userData.getUser().getDrivingLicense().getStatus();
        } catch (NullDrivingLicenseException e) {
            return DrivingLicense.DrivingLicenseStatus.ACTIVE;
        }
    }

    public Date getCreationLicenseDate(){
        try {
            return userData.getUser().getDrivingLicense().getCreateLicenseDate();
        } catch (NullDrivingLicenseException e) {
            return null;
        }
    }

}
