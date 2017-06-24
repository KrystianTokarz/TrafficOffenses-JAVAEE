package api.querymodel;

import api.exception.repository.user.DrivingLicenseNotFoundException;
import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import domainmodel.domain.event.EventItemType;
import readmodel.UserDataWithPointsVO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface PrivateUserDataFinder {
    UserDataWithPointsVO findDriverDataWithTheirPointsByPesel(String pesel) throws UserNotFoundException, UserNotActiveException;
    UserDataWithPointsVO findDriverDataWithTheirPointsByDrivingLicense(String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException;
    List<EventItemType> findAllRoadOffenses();
}
