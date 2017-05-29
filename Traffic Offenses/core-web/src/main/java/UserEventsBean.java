import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.PublicUserDataFinder;
import domainmodel.domain.event.Event;
import readmodel.UserEventsWithPointsVO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
        return userEventsWithPoints.getEvents().get(selectedEventIndex);
//                .stream()
//                .filter(e -> e.getAggregateRootEntityId() == selectedEventIndex)
//                .findFirst()
//                .get();
    }



    public String setSelectedEventInformation(int row) {

        selectedEventIndex = row;
        return "event-information.xhtml?faces-redirect=true";

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
