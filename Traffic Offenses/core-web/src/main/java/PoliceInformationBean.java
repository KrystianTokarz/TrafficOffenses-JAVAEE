import api.exception.UserNotActiveException;
import api.exception.repository.UserNotFoundException;
import api.readmodel.PrivateUserDataFinder;
import domainmodel.embaddable.DrivingLicense;
import exception.NullDrivingLicenseException;
import readmodel.UserDataWithPointsVO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class PoliceInformationBean {

    @EJB
    private PrivateUserDataFinder privateUserDataFinder;

    private UserDataWithPointsVO userDataWithPointsVO;

    private String pesel;
    private String drivingLicense;


    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public boolean hideOrShowDriverInformation(){
        if(userDataWithPointsVO == null)
            return false;
        else
            return true;
    }

    public void findDriver(){
        try {
            userDataWithPointsVO = privateUserDataFinder.findDriverDataWithTheirPoints(pesel,drivingLicense);
            System.out.println("true " + userDataWithPointsVO.getUser().getFirstName() );
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (UserNotActiveException e) {
            e.printStackTrace();

        }
    }

    public String getDriverFirstName(){
        if(userDataWithPointsVO == null)
            return null;
        else
            return userDataWithPointsVO.getUser().getFirstName();
    }

    public String getDriverLastName(){
        if(userDataWithPointsVO == null)
            return null;
        else
            return userDataWithPointsVO.getUser().getLastName();
    }

    public String getDriverPesel(){
        if(userDataWithPointsVO == null)
            return null;
        else
            return  userDataWithPointsVO.getUser().getPesel();
    }

    public String getNumberOfPoints(){
        if(userDataWithPointsVO == null)
            return null;
        else
            return String.valueOf(userDataWithPointsVO.getNumberOfPoints());
    }

    public String getDriverLicenseNumber(){
        if(userDataWithPointsVO == null)
            return null;
        else {
            try {
                return userDataWithPointsVO.getUser().getLicenseNumber();
            } catch (NullDrivingLicenseException e) {
                return "brak licencji";
            }
        }
    }

    public DrivingLicense.DrivingLicenseStatus getDrivingLicenseStatus(){
        if(userDataWithPointsVO == null)
            return null;
        else {
            try {
                return userDataWithPointsVO.getUser().getLicenseStatus();
            } catch (NullDrivingLicenseException e) {
                return null;
            }
        }
    }

}
