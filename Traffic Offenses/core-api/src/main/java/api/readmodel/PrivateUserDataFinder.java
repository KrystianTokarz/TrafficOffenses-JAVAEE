package api.readmodel;

import api.exception.UserNotActiveException;
import api.exception.repository.UserNotFoundException;
import readmodel.UserDataWithPointsVO;

import javax.ejb.Remote;

@Remote
public interface PrivateUserDataFinder {
    UserDataWithPointsVO findDriverDataWithTheirPoints(String pesel, String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException;
}
