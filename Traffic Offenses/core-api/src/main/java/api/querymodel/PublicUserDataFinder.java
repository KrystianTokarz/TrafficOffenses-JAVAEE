package api.querymodel;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import readmodel.SimpleUserDataVO;
import readmodel.UserEventsWithPointsVO;

import javax.ejb.Remote;

@Remote
public interface PublicUserDataFinder {
    SimpleUserDataVO findSimpleUserData(String pesel) throws UserNotFoundException, UserNotActiveException;
    UserEventsWithPointsVO findUserEvents(String pesel) throws UserNotFoundException, UserNotActiveException;
    UserEventsWithPointsVO findEventsAfterSelectedVersion(String pesel, Long version) throws UserNotFoundException, UserNotActiveException;
}
