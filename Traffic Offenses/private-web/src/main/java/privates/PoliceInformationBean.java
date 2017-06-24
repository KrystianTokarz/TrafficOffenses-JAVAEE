package privates;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.PrivateUserDataFinder;
import custom.code.MessageCode;
import exception.NullDrivingLicenseException;
import privates.backing.DriverInformationBean;

import readmodel.UserDataWithPointsVO;
import settings.FacesContextMessageBean;
import settings.ResourceBundleBean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ResourceBundle;

@ManagedBean
@ViewScoped
public class PoliceInformationBean implements Serializable{

    @EJB
    private PrivateUserDataFinder privateUserDataFinder;

    @ManagedProperty("#{driverInformationBean}")
    private DriverInformationBean driverInformationBean;

    @ManagedProperty("#{facesContextMessageBean}")
    private FacesContextMessageBean facesContextMessageBean;

    @ManagedProperty("#{resourceBundleBean}")
    private ResourceBundleBean resourceBundleBean;

    private UserDataWithPointsVO userDataWithPointsVO;

    private String pesel;

    private String drivingLicense;

    private static final String MANDATE_PAGE = "mandate.xhtml?faces-redirect=true";



    public String giveMandateToUser(){
        try {
            driverInformationBean.setLicenseNumber(userDataWithPointsVO.getUser().getDrivingLicense().getLicenseNumber());
        } catch (NullDrivingLicenseException e) {
            e.printStackTrace();
        }
        return MANDATE_PAGE;
    }


    public boolean hideOrShowDriverInformation(){
        return !(userDataWithPointsVO == null);
    }

    public void findDriverByPesel(){
        try {
            drivingLicense = "";
            userDataWithPointsVO = privateUserDataFinder.findDriverDataWithTheirPointsByPesel(pesel);
        } catch (UserNotFoundException e) {
            showSearchedUserIsNotFound();
        } catch (UserNotActiveException e) {
            showSearchedUserIsNotActive();
        }
    }

    public void findDriverByDrivingLicenseNumber(){
        try {
            pesel = "";
            userDataWithPointsVO = privateUserDataFinder.findDriverDataWithTheirPointsByDrivingLicense(drivingLicense);
        } catch (UserNotFoundException e) {
            showSearchedUserIsNotFound();
        } catch (UserNotActiveException e) {
            showSearchedUserIsNotActive();
        }
    }

    private void clearUserData(){
        userDataWithPointsVO = null;
    }

    private void showSearchedUserIsNotFound(){
        clearUserData();
        facesContextMessageBean.showMessage(FacesMessage.SEVERITY_ERROR, MessageCode.USER_NOT_FOUND);
    }

    private void showSearchedUserIsNotActive(){
        clearUserData();
        facesContextMessageBean.showMessage(FacesMessage.SEVERITY_ERROR,MessageCode.USER_NOT_ACTIVE);
    }

    public String getSearchedDriverFirstName(){
            return userDataWithPointsVO.getUser().getFirstName();
    }

    public String getSearchedDriverLastName(){
            return userDataWithPointsVO.getUser().getLastName();
    }

    public String getSearchedDriverPesel(){
            return  userDataWithPointsVO.getUser().getPesel();
    }

    public String getSearchedNumberOfPoints(){
            return String.valueOf(userDataWithPointsVO.getNumberOfPoints());
    }

    public String getSearchedDriverLicenseNumber(){
        try {
            return userDataWithPointsVO.getUser().getDrivingLicense().getLicenseNumber();
        } catch (NullDrivingLicenseException e) {
            return returnNoLicenseBundleString();
        }
    }

    private String returnNoLicenseBundleString(){
        ResourceBundle bundle = resourceBundleBean.getBundle();
        return bundle.getString("information-code.NO_DRIVING_LICENSE ");
    }

    public String getSearchedDrivingLicenseStatus(){
        try {
            return userDataWithPointsVO.getUser().getDrivingLicense().getStatus().name();
        } catch (NullDrivingLicenseException e) {
            return null;
        }
    }


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

    public void setDriverInformationBean(DriverInformationBean driverInformationBean) {
        this.driverInformationBean = driverInformationBean;
    }

    public void setFacesContextMessageBean(FacesContextMessageBean facesContextMessageBean) {
        this.facesContextMessageBean = facesContextMessageBean;
    }

    public void setResourceBundleBean(ResourceBundleBean resourceBundleBean) {
        this.resourceBundleBean = resourceBundleBean;
    }
}
