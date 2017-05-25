package cqrs.querymodel.finder.helper;

import api.exception.repository.event.EventsWithVersionNotFoundException;
import cqrs.querymodel.api.EventHelperFinder;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import infrastructure.repository.api.EventRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.util.Collections;
import java.util.List;

@Stateless
public class EventHelperFinderBean implements EventHelperFinder {

    @EJB
    private EventRepository eventRepository;

    @Override
    public List<Event> findUserEvents(Long userId, Snapshot snapshot)  {

        try {
            return eventRepository.findEventsWithVersion(userId, snapshot.getEventVersion());
        } catch (EventsWithVersionNotFoundException e) {
            return Collections.emptyList();
        }

    }
}
