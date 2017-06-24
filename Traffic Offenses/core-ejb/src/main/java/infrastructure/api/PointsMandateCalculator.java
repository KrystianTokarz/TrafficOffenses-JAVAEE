package infrastructure.api;

import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PointsMandateCalculator {

    int calculateUserPoints(List<Event> events, Snapshot snapshot);

    int calculateUserPoints(Event event);
}
