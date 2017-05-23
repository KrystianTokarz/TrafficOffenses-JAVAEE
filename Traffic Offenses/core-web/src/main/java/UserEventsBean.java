import api.exception.UserNotActiveException;
import api.exception.repository.UserNotFoundException;
import api.readmodel.PublicUserDataFinder;
import domainmodel.domain.event.Event;
import readmodel.UserEventsWithPointsVO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "userEventsBean")
@SessionScoped
public class UserEventsBean implements Serializable {
    @EJB
    private PublicUserDataFinder publicUserFinder;

    private UserEventsWithPointsVO userEventsWithPoints;

    private int selectedEventIndex;


    public Event getSelectedEvent(){
        return userEventsWithPoints.getEvents()
                .stream()
                .filter(e -> e.getAggregateRootEntityId() == selectedEventIndex)
                .findFirst()
                .get();
    }

    public void setSelectedEventIndex(int s){
        this.selectedEventIndex = s;
    }


    public String setSelectedEventInformation(int row) {

        selectedEventIndex = row;
        return "event-information.xhtml";

    }

    @PostConstruct
    public void init(){
        String pesel = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();

        try {
            userEventsWithPoints = publicUserFinder.findUserEventsWithPoints(pesel);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (UserNotActiveException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getUserEvents(){
        return userEventsWithPoints.getEvents();
    }

    public int getUserPoints(){
        return userEventsWithPoints.getNumberOfPoints();
    }

}
