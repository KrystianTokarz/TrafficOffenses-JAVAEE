//package api.querymodel.manager;
//
//import api.exception.UserNotActiveException;
//import api.exception.repository.EventsNotFoundException;
//import api.exception.repository.UserNotFoundException;
//import domainmodel.domain.event.Event;
//import domainmodel.domain.snapshot.Snapshot;
//import domainmodel.domain.user.User;
//
//import javax.ejb.Remote;
//import java.util.List;
//
//@Remote
//public interface MainUserFinder {
//
//    CompleteUserInformationVO findUserData(String pesel, String licenseNumber) throws UserNotFoundException, UserNotActiveException, EventsNotFoundException;
//
//    CompleteUserInformationVO createVisualUserData(User user, Snapshot snapshot, List<Event> events);
//}
