package admin;

import api.commandmodel.SynchronousCommandFacade;
import api.exception.command.CommandValidationException;
import api.exception.command.SynchronousCommandException;
import custom.code.MessageCode;
import domainmodel.domain.user.Role;
import domainmodel.embaddable.DrivingLicense;
import settings.FacesContextMessageBean;
import writemodel.SavePrivateOrAdministratorUserCommand;
import writemodel.SavePublicUserCommand;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@ManagedBean
@ViewScoped
public class CreateUserBean{

    @EJB
    private SynchronousCommandFacade commandFacade;

    @ManagedProperty("#{facesContextMessageBean}")
    private FacesContextMessageBean facesContextMessageBean;

    private String userAccountType;

    private boolean userHasDrivingLicense = false;

    @Size(min=3,max=15)
    private String firstName;

    @Size(min=3,max=15)
    private String lastName;

    @Pattern(regexp = "^\\d{11}$")
    private String pesel;

    @Size(min=3,max=15)
    private String password;

    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @Past
    private Date licenseCreationDate;

    @Pattern(regexp = "\\d{5}\\/\\d{2}\\/\\d{4}$")
    private String licenseNumber;

    private DrivingLicense.DrivingLicenseStatus licenseStatus;


    public void savePublicUser(){
        SavePublicUserCommand command = new SavePublicUserCommand(firstName,
                lastName,email,pesel,licenseNumber,licenseCreationDate,licenseStatus);

        try {
            commandFacade.sendCommand(command);
            facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, MessageCode.SUCCESS_CREATE_PUBLIC_USER);
        } catch (CommandValidationException e) {
            facesContextMessageBean.showCommandValidationError(e);
        } catch (SynchronousCommandException e) {
            facesContextMessageBean.showSynchronousCommandException(e);
        }
    }

    public void savePrivateUser(){
        SavePrivateOrAdministratorUserCommand command = createPrivateOrAdministratorUserCommand(Role.ROLE_PRIVATE);

        try {
            commandFacade.sendCommand(command);
            facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, MessageCode.SUCCESS_CREATE_PRIVATE_USER);
        } catch (CommandValidationException e) {
            facesContextMessageBean.showCommandValidationError(e);
        } catch (SynchronousCommandException e) {
            facesContextMessageBean.showSynchronousCommandException(e);
        }
    }

    public void saveAdministratorUser() {
        SavePrivateOrAdministratorUserCommand command = createPrivateOrAdministratorUserCommand(Role.ROLE_ADMINISTRATOR);
        try {
            commandFacade.sendCommand(command);;
            facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, MessageCode.SUCCESS_CREATE_ADMINISTRATOR_USER);
        } catch (CommandValidationException e) {
            facesContextMessageBean.showCommandValidationError(e);
        } catch (SynchronousCommandException e) {
            facesContextMessageBean.showSynchronousCommandException(e);
        }
    }


    private SavePrivateOrAdministratorUserCommand createPrivateOrAdministratorUserCommand(Role role){
        SavePrivateOrAdministratorUserCommand command;
        if(userHasDrivingLicense){
            command = new SavePrivateOrAdministratorUserCommand(firstName, lastName, email, pesel, password,role, licenseNumber,licenseCreationDate,licenseStatus);
        } else {
            command = new SavePrivateOrAdministratorUserCommand(firstName, lastName, email, pesel, password,role);
        }
        return command;
    }


    public boolean isUserHasDrivingLicense() {
        return userHasDrivingLicense;
    }

    public void setUserHasDrivingLicense(boolean userHasDrivingLicense) {
        this.userHasDrivingLicense = userHasDrivingLicense;
    }

    public String getUserAccountType() {
        return userAccountType;
    }

    public void setUserAccountType(String userAccountType) {
        this.userAccountType = userAccountType;

    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLicenseCreationDate() {
        return licenseCreationDate;
    }

    public void setLicenseCreationDate(Date licenseCreationDate) {
        this.licenseCreationDate = licenseCreationDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public DrivingLicense.DrivingLicenseStatus getLicenseStatus() {
        return licenseStatus;
    }

    public void setLicenseStatus(DrivingLicense.DrivingLicenseStatus licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public FacesContextMessageBean getFacesContextMessageBean() {
        return facesContextMessageBean;
    }

    public void setFacesContextMessageBean(FacesContextMessageBean facesContextMessageBean) {
        this.facesContextMessageBean = facesContextMessageBean;
    }
}
