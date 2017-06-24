import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.PublicUserDataFinder;
import domainmodel.domain.event.Event;
import readmodel.UserEventsWithPointsVO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class UserEventsBean implements Serializable {

    @EJB
    private PublicUserDataFinder publicUserFinder;

    @ManagedProperty(value="#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    private UserEventsWithPointsVO userEventsWithPoints;

    private int selectedEventIndex;

    private String pesel;

    private static final String EVENT_INFORMATION_PAGE = "event-information.xhtml?faces-redirect=true";



    @PostConstruct
    public void init() throws UserNotFoundException, UserNotActiveException {
        pesel = authenticationBean.getUserPesel();
        userEventsWithPoints = publicUserFinder.findUserEvents(pesel);

    }

    public boolean userCanLoadOldEvents(){
        return (userEventsWithPoints.getSnapshotVersion() == 0);
    }

    public Event getSelectedEvent(){
        return userEventsWithPoints.getEvents().get(selectedEventIndex);
    }

    public String setSelectedEventInformation(int row) {
        selectedEventIndex = row;
        return EVENT_INFORMATION_PAGE;

    }


    public List<Event> getUserEvents(){
        return userEventsWithPoints.getEvents();
    }

    public int getUserPoints(){
        return userEventsWithPoints.getNumberOfPoints();
    }

    public void loadEventsAfterSelectedVersion() throws UserNotFoundException, UserNotActiveException {
        long version = userEventsWithPoints.getSnapshotVersion() -1;
        userEventsWithPoints = publicUserFinder.findEventsAfterSelectedVersion(pesel, version);
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }
}
