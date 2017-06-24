package infrastructure.repository.api;

import api.exception.repository.user.DrivingLicenseNotFoundException;
import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.AggregateNotActiveException;
import api.exception.repository.AggregateNotFoundException;
import api.exception.repository.user.UserNotFoundException;
import domainmodel.domain.user.User;
import domainmodel.domain.user.UserRole;
import domainmodel.embaddable.DrivingLicense;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserRepository {

    User load(Long aggregateId) throws  AggregateNotFoundException;

    void persist(User user);

    User merge(User user);

    void delete(Long aggregateId) throws  AggregateNotFoundException;

    User findUserByPeselAndDrivingLicenseNumber(String pesel, String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException;

    User findUserByPesel(String pesel) throws UserNotFoundException, UserNotActiveException;

    User findUserByDrivingLicenseNumber(String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException;

    List<User> findAllActiveDrivers();

    void deleteUserRole(UserRole userRole);

    List<User> findAllUser();

}
