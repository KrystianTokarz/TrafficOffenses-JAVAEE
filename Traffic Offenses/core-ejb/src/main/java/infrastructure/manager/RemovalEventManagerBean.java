package infrastructure.manager;

import api.exception.repository.AggregateNotActiveException;
import api.exception.repository.AggregateNotFoundException;
import api.exception.repository.snapshot.SnapshotNotFoundException;
import cqrs.command.api.RemovalEventManager;
import domainmodel.domain.event.Event;
import domainmodel.domain.event.EventItem;
import domainmodel.domain.event.EventItemType;
import domainmodel.domain.event.EventType;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.user.User;
import infrastructure.api.EventManager;
import infrastructure.api.SnapshotManager;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.api.SnapshotRepository;
import infrastructure.repository.api.UserRepository;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RemovalEventManagerBean implements RemovalEventManager {

    @EJB
    private EventRepository eventRepository;

    @EJB
    private SnapshotManager snapshotManager;

    @EJB
    private SnapshotRepository snapshotRepository;

    @EJB
    private UserRepository userRepository;

    @Inject
    private Logger logger;

    @Override
    public void createEventToDeletePointsAfterYear(Long eventId){
        try {
            Event event = eventRepository.load(eventId);
            Long userId = event.getUserData().getUserAggregateRootId();
            if(checkUserIsActive(userId)) {
                Snapshot lastSnapshot = snapshotManager.findLastSnapshot(userId);
                Event newEvent = createNewEventForDeletePoints(event, lastSnapshot.getEventVersion());
                eventRepository.persist(newEvent);
            }
        } catch (AggregateNotActiveException | AggregateNotFoundException e ) {
            logger.info("Event with id = %,d is not possible to reset", eventId);
        }
    }

    private Event createNewEventForDeletePoints(Event eventToDelete, Long version){

        Event newEvent = new Event(eventToDelete.getUserData(), EventType.MANDATE_REMOVER, version);
        List<EventItem> eventItemList = new ArrayList<>(eventToDelete.getEventItemList());
        for (EventItem item: eventItemList) {
            int pointsToDelete = item.getEventItemType().getPoints() * (-1);
            newEvent.addEventItem(new EventItem(item.getEventItemType(),0,pointsToDelete));
        }
        return newEvent;
    }

    private boolean checkUserIsActive(Long userId){
        boolean result;
        try {
            User user = userRepository.load(userId);
            result = user.isActive();
        } catch (AggregateNotFoundException e) {
            result = false;
        }
        return result;
    }
}
