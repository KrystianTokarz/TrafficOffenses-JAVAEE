package admin;

import admin.backing.UserAccountBean;
import api.commandmodel.SynchronousCommandFacade;
import api.exception.command.CommandValidationException;
import api.exception.command.SynchronousCommandException;
import api.querymodel.AdministratorDataFinder;
import custom.code.MessageCode;
import domainmodel.domain.user.User;
import exception.NullDrivingLicenseException;
import settings.FacesContextMessageBean;
import writemodel.ActiveUserCommand;
import writemodel.Command;
import writemodel.DeleteUserCommand;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class UserListBean implements Serializable{

    @EJB
    private SynchronousCommandFacade commandFacade;

    @EJB
    private AdministratorDataFinder administratorDataFinder;

    @ManagedProperty("#{facesContextMessageBean}")
    private FacesContextMessageBean facesContextMessageBean;

    @ManagedProperty("#{userAccountBean}")
    private UserAccountBean userAccountBean;

    public List<User> userList;

    private static final String EDIT_USER_PAGE = "edit-user.xhtml?faces-redirect=true";


    @PostConstruct
    private void init(){
        userList = administratorDataFinder.findAllUser();
    }

    public boolean checkUserHasDrivingLicense(User user){
        boolean result;
        try {
            user.getDrivingLicense();
            result= true;
        } catch (NullDrivingLicenseException e) {
            result = false;
        }
        return result;
    }

    public void deleteSelectedUser(User user){
        Command command = new DeleteUserCommand(user.getAggregateRootEntityId());
        sendCommand(command);
        facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, MessageCode.SUCCESS_INACTIVE_USER);
    }

    public void activeSelectedUser(User user){
        Command command = new ActiveUserCommand(user.getAggregateRootEntityId());
        sendCommand(command);
        facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, MessageCode.SUCCESS_ACTIVE_USER);
    }

    private void sendCommand(Command command){
        try {
            commandFacade.sendCommand(command);
            init();
        } catch (CommandValidationException e) {
            facesContextMessageBean.showCommandValidationError(e);
        } catch (SynchronousCommandException e) {
            facesContextMessageBean.showSynchronousCommandException(e);
        }
    }

    public String editSelectedUser(User user){
        userAccountBean.setUser(user);
        return EDIT_USER_PAGE;
    }

    public boolean checkUserIsActive(User user){
        return user.isActive();
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public FacesContextMessageBean getFacesContextMessageBean() {
        return facesContextMessageBean;
    }

    public void setFacesContextMessageBean(FacesContextMessageBean facesContextMessageBean) {
        this.facesContextMessageBean = facesContextMessageBean;
    }

    public UserAccountBean getUserAccountBean() {
        return userAccountBean;
    }

    public void setUserAccountBean(UserAccountBean userAccountBean) {
        this.userAccountBean = userAccountBean;
    }




}
