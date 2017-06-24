package readmodel;

import domainmodel.domain.event.Event;

import java.io.Serializable;
import java.util.List;

public class UserEventsWithPointsVO implements Serializable{

    private List<Event> events;

    private int numberOfPoints;

    private long snapshotVersion;

    public UserEventsWithPointsVO(List<Event> events, int numberOfPoints, Long snapshotVersion) {
        this.events = events;
        this.numberOfPoints = numberOfPoints;
        this.snapshotVersion = snapshotVersion;
    }

    public List<Event> getEvents() {
        return events;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public long getSnapshotVersion() {
        return snapshotVersion;
    }
}
