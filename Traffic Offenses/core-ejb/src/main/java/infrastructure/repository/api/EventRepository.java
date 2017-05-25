package infrastructure.repository.api;


import api.exception.repository.AggregateNotActiveException;
import api.exception.repository.AggregateNotFoundException;
import api.exception.repository.event.EventsNotFoundException;
import api.exception.repository.event.EventsWithVersionNotFoundException;
import domainmodel.domain.event.Event;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EventRepository {

    Event load(Long aggregateId) throws AggregateNotActiveException, AggregateNotFoundException;

    void persist(Event event);

    Event merge(Event event);

    void delete(Long aggregateId) throws AggregateNotActiveException, AggregateNotFoundException;

    List<Event> findEventsWithVersion(Long userId, Long version ) throws EventsWithVersionNotFoundException;

    List<Event> findEvents(Long userId) throws EventsNotFoundException;
}
