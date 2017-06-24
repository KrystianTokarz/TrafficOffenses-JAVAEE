package infrastructure.manager;

import api.exception.repository.event.EventsAfterSelectedVersionNotFoundException;
import api.exception.repository.event.EventsWithVersionNotFoundException;
import api.exception.repository.snapshot.SnapshotNotFoundException;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import infrastructure.api.EventManager;
import infrastructure.api.SnapshotManager;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.api.SnapshotRepository;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Stateless
public class EventManagerBean implements EventManager {

    @EJB
    private EventRepository eventRepository;

    @EJB
    private SnapshotManager snapshotRepository;

    @Inject
    private Logger logger;

    @Override
    public List<Event> findEventsForLastSnapshot(Long userId){

        Snapshot lastSnapshot = snapshotRepository.findLastSnapshot(userId);
        return findEventsWithVersion(userId, lastSnapshot.getEventVersion());
    }

    @Override
    public List<Event> findEventsWithAndAfterSelectedVersion(Long userId, Long version){
        List<Event> eventList = null;
        try {
            eventList =  eventRepository.findEventsWithAndAfterSelectedVersion(userId, version);
        } catch (EventsAfterSelectedVersionNotFoundException e) {
            logger.debug("Event after version = {0} not found",version);
            eventList = Collections.emptyList();
        }
        return eventList;
    }


    private List<Event> findEventsWithVersion(Long userId, Long version)  {
        List<Event> eventList = null;
        try {
            eventList =  eventRepository.findEventsWithVersion(userId, version);
        } catch (EventsWithVersionNotFoundException e) {
            logger.debug("Event with version = {0} not found",version);
            eventList = Collections.emptyList();
        }
        return eventList;
    }


}
