package privates;


import api.commandmodel.AsynchronousCommandFacade;
import api.exception.command.CommandValidationException;
import api.querymodel.PrivateUserDataFinder;
import custom.code.MessageCode;
import domainmodel.domain.event.EventItem;
import domainmodel.domain.event.EventItemType;
import privates.backing.DriverInformationBean;
import settings.FacesContextMessageBean;
import writemodel.Command;
import writemodel.CreateMandateCommand;

import javax.annotation.PostConstruct;
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
public class MandateBean implements Serializable {


    @EJB
    private AsynchronousCommandFacade commandFacade;

    @EJB
    private PrivateUserDataFinder privateUserDataFinder;

    @ManagedProperty("#{facesContextMessageBean}")
    private FacesContextMessageBean facesContextMessageBean;

    @ManagedProperty("#{driverInformationBean}")
    private DriverInformationBean driverInformationBean;

    private String drivingLicenseNumber;

    private List<EventItemType> roadOffensesList;

    private List<EventItem> selectedRoadOffensesList = new ArrayList<>();

    private boolean hideOrShowDrivingLicenseNumberInput = false;


    @PostConstruct
    public void init() {

        this.drivingLicenseNumber = driverInformationBean.getLicenseNumber();
        if(drivingLicenseNumber != null && (!drivingLicenseNumber.isEmpty()))
            hideOrShowDrivingLicenseNumberInput = true;
        driverInformationBean.clearData();
        roadOffensesList = privateUserDataFinder.findAllRoadOffenses();
    }

    public void createMandate(){
        Command command = new CreateMandateCommand(drivingLicenseNumber, selectedRoadOffensesList);
        try {
            commandFacade.sendCommand(command);
            facesContextMessageBean.showMessage(FacesMessage.SEVERITY_INFO, MessageCode.SUCCESS_CREATE_MANDATE);
            clearSelectedOffenses();
        } catch (CommandValidationException exception) {
            facesContextMessageBean.showCommandValidationError(exception);
        }
    }

    public boolean canCreateMandate(){
        return ((selectedRoadOffensesList.isEmpty()));
    }

    private void clearSelectedOffenses(){
        this.selectedRoadOffensesList.clear();
    }


    public void addSelectedRoadOffenses(EventItemType eventItemType){
        selectedRoadOffensesList.add(new EventItem(eventItemType,0,eventItemType.getPoints()));
    }

    public void deleteSelectedRoadOffenses(EventItem mandatePosition){
        selectedRoadOffensesList.remove(mandatePosition);
    }

    public List<EventItem> getSelectedRoadOffensesList() {
        return selectedRoadOffensesList;
    }

    public void setSelectedRoadOffensesList(List<EventItem> selectedRoadOffensesList) {
        this.selectedRoadOffensesList = selectedRoadOffensesList;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public List<EventItemType> getRoadOffensesList() {
        return roadOffensesList;
    }

    public void setRoadOffensesList(List<EventItemType> roadOffensesList) {
        this.roadOffensesList = roadOffensesList;
    }


    public boolean isHideOrShowDrivingLicenseNumberInput() {
        return hideOrShowDrivingLicenseNumberInput;
    }

    public DriverInformationBean getDriverInformationBean() {
        return driverInformationBean;
    }

    public void setDriverInformationBean(DriverInformationBean driverInformationBean) {
        this.driverInformationBean = driverInformationBean;
    }


    public FacesContextMessageBean getFacesContextMessageBean() {
        return facesContextMessageBean;
    }

    public void setFacesContextMessageBean(FacesContextMessageBean facesContextMessageBean) {
        this.facesContextMessageBean = facesContextMessageBean;
    }
}
