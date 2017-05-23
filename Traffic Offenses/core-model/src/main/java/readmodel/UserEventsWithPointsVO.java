package readmodel;

import domainmodel.domain.event.Event;

import java.io.Serializable;
import java.util.List;

public class UserEventsWithPointsVO implements Serializable{

    private List<Event> events;

    private int numberOfPoints;

    public UserEventsWithPointsVO(List<Event> events, int numberOfPoints) {
        this.events = events;
        this.numberOfPoints = numberOfPoints;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }
}
