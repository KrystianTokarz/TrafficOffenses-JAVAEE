//package cqrs.querymodel.facade;
//
//import api.exception.repository.AggregateNotActiveException;
//import api.exception.repository.AggregateNotFoundException;
//import api.exception.repository.EventsNotFoundException;
//import api.exception.repository.EventsWithVersionNotFoundException;
//import domainmodel.domain.event.Event;
//import infrastructure.repository.api.EventRepository;
//
//import javax.ejb.EJB;
//import javax.ejb.Schedule;
//import javax.ejb.Stateless;
//import java.util.Collections;
//import java.util.List;
//
//@Stateless
//public class EventFinderBean {
//
//    @EJB
//    private EventRepository eventRepository;
//
//    private final String day = "1";
//    @Schedule(second = day)
//    public List<Event> findEventsWithSnapshot(Long userId, Long version){
//        List<Event> events;
//        try {
//            events = eventRepository.findEventsWithVersion(userId,version);
//        } catch (EventsWithVersionNotFoundException e) {
//            return Collections.emptyList();
//        }
//        return events;
//    }
//
//
//    public List<Event> findEventsWithoutSnapshot(Long userId){
//        List<Event> events;
//        try {
//            events = eventRepository.findEvents(userId);
//        } catch (EventsNotFoundException e) {
//            return Collections.emptyList();
//        }
//        return events;
//    }
//
//    public Event findSelectedEvent(Long eventId) throws AggregateNotActiveException, AggregateNotFoundException {
//        return eventRepository.load(eventId);
//    }
//}
