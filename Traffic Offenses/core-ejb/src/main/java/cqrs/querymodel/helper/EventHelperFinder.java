package cqrs.querymodel.helper;

import api.exception.repository.EventsWithVersionNotFoundException;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import infrastructure.repository.api.EventRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.util.Collections;
import java.util.List;

@Stateless
public class EventHelperFinder {

    @EJB
    private EventRepository eventRepository;

    public List<Event> findUserEvents(Long userId, Snapshot snapshot)  {

        try {
            return eventRepository.findEventsWithVersion(userId, snapshot.getEventVersion());
        } catch (EventsWithVersionNotFoundException e) {
            return Collections.emptyList();
        }

    }
}
