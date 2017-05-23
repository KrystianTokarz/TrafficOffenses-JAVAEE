package infrastructure.repository.api;

import api.exception.UserNotActiveException;
import api.exception.repository.AggregateNotActiveException;
import api.exception.repository.AggregateNotFoundException;
import api.exception.repository.UserNotFoundException;
import domainmodel.domain.user.User;

import javax.ejb.Local;

@Local
public interface UserRepository {

    User load(Long aggregateId) throws AggregateNotActiveException, AggregateNotFoundException;

    void persist(User user);

    User merge(User user);

    void delete(Long aggregateId) throws AggregateNotActiveException, AggregateNotFoundException;

    User findUserByPeselAndDrivingLicenseNumber(String pesel, String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException;

    User findUserByPesel(String pesel) throws UserNotFoundException, UserNotActiveException;
}
