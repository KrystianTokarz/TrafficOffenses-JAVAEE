//package api.querymodel.manager;
//
//import api.exception.repository.AggregateNotActiveException;
//import api.exception.repository.AggregateNotFoundException;
//import domainmodel.domain.event.Event;
//
//import javax.ejb.Remote;
//
//@Remote
//public interface MainEventFinder {
//    EventDataVO findEventData(Long eventId) throws AggregateNotActiveException, AggregateNotFoundException;
//
//    EventDataVO createVisualEventData(Event event);
//}
