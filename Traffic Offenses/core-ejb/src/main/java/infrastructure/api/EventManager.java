package infrastructure.api;

import domainmodel.domain.event.Event;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EventManager {

    List<Event> findEventsForLastSnapshot(Long userId);

    List<Event> findEventsWithAndAfterSelectedVersion(Long userId, Long version);

}
