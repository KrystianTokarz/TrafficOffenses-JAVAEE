package infrastructure.repository.implementation;

import api.exception.repository.event.EventsNotFoundException;
import api.exception.repository.event.EventsWithVersionNotFoundException;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.GenericRepository;
import domainmodel.domain.event.Event;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class DefaultEventRepository extends GenericRepository<Event> implements EventRepository{


    public List<Event> findEventsWithVersion(Long userId, Long version) throws EventsWithVersionNotFoundException {

        List<Event> events = entityManager.createNamedQuery("Event.findEventsWithVersion", Event.class)
                .setParameter("userId", userId)
                .setParameter("version", version)
                .getResultList();

        if(events == null)
            throw new EventsWithVersionNotFoundException("Events for user with id = " + userId + " and version = " + version + " does not found");

        return events;
    }

    public List<Event> findEvents(Long userId) throws EventsNotFoundException {

        List<Event> events = entityManager.createNamedQuery("Event.findAllEvents", Event.class)
                .setParameter("userId", userId)
                .getResultList();

        if(events == null)
            throw new EventsNotFoundException("Events for user with id = " + userId + " does not found");

        return events;
    }
}
