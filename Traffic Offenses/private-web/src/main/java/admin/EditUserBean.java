package admin;

import admin.backing.UserAccountBean;
import api.commandmodel.SynchronousCommandFacade;
import api.exception.command.CommandValidationException;
import api.exception.command.SynchronousCommandException;
import custom.code.MessageCode;
import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;
import exception.NullDrivingLicenseException;
import settings.FacesContextMessageBean;
import writemodel.Command;
import writemodel.EditUserCommand;
import writemodel.SavePublicUserCommand;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.ejb.PostActivate;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class EditUserBean implements Serializable {


    @ManagedProperty("#{userAccountBean}")
    private UserAccountBean userAccountBean;

    @ManagedProperty("#{facesContextMessageBean}")
    private FacesContextMessageBean facesContextMessageBean;

    @EJB
    private SynchronousCommandFacade commandFacade;

    private boolean addUserDrivingLicense = false;

    private User user;

    private DrivingLicense drivingLicense = new DrivingLicense();

    @PostConstruct
    private void init(){
        user = userAccountBean.getUser();
    }

    public boolean checkUserHasDrivingLicense(){
        boolean result;
        try {
            user.getDrivingLicense();
            result= true;
        } catch (NullDrivingLicenseException e) {
            result = false;
        }
        return result;
    }

    public void editSelectedUser(){

        Command command;
        if(addUserDrivingLicense){
            command = new EditUserCommand(user, drivingLicense);
        }else{
            command = new EditUserCommand(user, null);
        }
        sendCommand(command);
    }

    private void sendCommand(Command command){
        try {
            commandFacade.sendCommand(command);
            facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, MessageCode.SUCCESS_EDIT_USER_ACCOUNT);
        } catch (CommandValidationException e)   {
            facesContextMessageBean.showCommandValidationError(e);
        } catch (SynchronousCommandException e) {
            facesContextMessageBean.showSynchronousCommandException(e);
        }
    }

    public DrivingLicense getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(DrivingLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public UserAccountBean getUserAccountBean() {
        return userAccountBean;
    }

    public void setUserAccountBean(UserAccountBean userAccountBean) {
        this.userAccountBean = userAccountBean;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAddUserDrivingLicense() {
        return addUserDrivingLicense;
    }

    public void setAddUserDrivingLicense(boolean addUserDrivingLicense) {
        this.addUserDrivingLicense = addUserDrivingLicense;
    }

    public FacesContextMessageBean getFacesContextMessageBean() {
        return facesContextMessageBean;
    }

    public void setFacesContextMessageBean(FacesContextMessageBean facesContextMessageBean) {
        this.facesContextMessageBean = facesContextMessageBean;
    }
}
