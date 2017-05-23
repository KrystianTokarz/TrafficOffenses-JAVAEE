package api.readmodel;

import api.exception.UserNotActiveException;
import api.exception.repository.UserNotFoundException;
import readmodel.SimpleUserDataVO;
import readmodel.UserEventsWithPointsVO;

import javax.ejb.Remote;

@Remote
public interface PublicUserDataFinder {
    SimpleUserDataVO findSimpleUserData(String pesel) throws UserNotFoundException, UserNotActiveException;

    UserEventsWithPointsVO findUserEventsWithPoints(String pesel) throws UserNotFoundException, UserNotActiveException;
}
