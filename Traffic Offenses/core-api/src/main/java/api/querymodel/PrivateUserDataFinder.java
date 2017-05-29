package api.querymodel;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import readmodel.UserDataWithPointsVO;

import javax.ejb.Remote;

@Remote
public interface PrivateUserDataFinder {
    UserDataWithPointsVO findDriverDataWithTheirPoints(String pesel, String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException;
}
