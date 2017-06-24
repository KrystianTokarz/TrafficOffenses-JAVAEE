package admin;

import api.commandmodel.SynchronousCommandFacade;
import api.exception.command.CommandValidationException;
import api.exception.command.SynchronousCommandException;
import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.AdministratorDataFinder;
import custom.code.MessageCode;
import domainmodel.domain.user.Role;
import domainmodel.domain.user.User;
import domainmodel.domain.user.UserRole;

import readmodel.SimpleUserDataVO;
import settings.FacesContextMessageBean;
import writemodel.AddNewRoleForUserCommand;
import writemodel.Command;
import writemodel.DeleteRoleForUserCommand;

import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class UserRolesBean implements Serializable{

    @EJB
    private AdministratorDataFinder administratorUserDataFinder;

    @EJB
    private SynchronousCommandFacade commandFacade;

    @ManagedProperty("#{facesContextMessageBean}")
    private FacesContextMessageBean facesContextMessageBean;

    private User user;

    private String pesel;

    private List<UserRole> userRoles;

    private List<UserRole> newUserRolesList;

    private String selectedPesel;

    private static final String ROLE_PRIVATE = "ROLE_PRIVATE";


    public void searchUserByPesel(){
        try {
            if(selectedPesel != pesel)
                selectedPesel = pesel;
            SimpleUserDataVO userDataVO = administratorUserDataFinder.findUserWithTheirRolesByPesel(selectedPesel);
            user = userDataVO.getUser();
            userRoles =  new ArrayList<>(user.getUserRoles());
        } catch (UserNotFoundException e) {
            facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, MessageCode.USER_NOT_FOUND);
            selectedPesel = null;
            user = null;
        } catch (UserNotActiveException e) {
            facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, MessageCode.USER_NOT_ACTIVE);
            selectedPesel = null;
            user = null;
        }
    }

    public void addSelectedRole(String role){
        Command command = new AddNewRoleForUserCommand(selectedPesel, role);
        if(role.equals(ROLE_PRIVATE))
             sendSelectedCommand(command,MessageCode.SUCCESS_ADD_PRIVATE_ROLE);
        else
            sendSelectedCommand(command,MessageCode.SUCCESS_ADD_ADMINISTRATOR_ROLE);
    }

    public void deleteSelectedRole(String role){
        Command command = new DeleteRoleForUserCommand(selectedPesel, role);
        if(role.equals(ROLE_PRIVATE))
             sendSelectedCommand(command,MessageCode.SUCCESS_REMOVE_PRIVATE_ROLE);
        else
            sendSelectedCommand(command,MessageCode.SUCCESS_REMOVE_ADMINISTRATOR_ROLE);

    }

    private void sendSelectedCommand(Command command,MessageCode messageCode){
        try {
            commandFacade.sendCommand(command);
            searchUserByPesel();
            facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, messageCode);
        } catch (CommandValidationException e) {
            facesContextMessageBean.showCommandValidationError(e);
        } catch (SynchronousCommandException e) {
            facesContextMessageBean.showSynchronousCommandException(e);
        }
    }


    public boolean hasPrivateRole(){
        boolean result = false;
        for (UserRole userRole: userRoles) {
            if(userRole.getRole().equals(Role.ROLE_PRIVATE)){
                result = true;
            }
        }
        return result;
    }

    public boolean hasAdministrator(){
        boolean result = false;
        for (UserRole userRole: userRoles) {
            if(userRole.getRole().equals(Role.ROLE_ADMINISTRATOR)){
                result = true;
            }
        }
        return result;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public FacesContextMessageBean getFacesContextMessageBean() {
        return facesContextMessageBean;
    }

    public void setFacesContextMessageBean(FacesContextMessageBean facesContextMessageBean) {
        this.facesContextMessageBean = facesContextMessageBean;
    }

    public List<UserRole> getNewUserRolesList() {
        return newUserRolesList;
    }

    public void setNewUserRolesList(List<UserRole> newUserRolesList) {
        this.newUserRolesList = newUserRolesList;
    }
}
