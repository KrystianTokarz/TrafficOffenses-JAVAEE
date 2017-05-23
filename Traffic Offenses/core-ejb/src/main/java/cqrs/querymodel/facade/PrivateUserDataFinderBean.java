package cqrs.querymodel.facade;

import api.exception.UserNotActiveException;
import api.exception.repository.SnapshotNotFoundException;
import api.exception.repository.UserNotFoundException;
import api.readmodel.PrivateUserDataFinder;
import cqrs.querymodel.helper.EventHelperFinder;
import cqrs.querymodel.helper.SnapshotHelperFinder;
import cqrs.querymodel.utility.VisualObjectDataTransfer;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotEntity;
import domainmodel.domain.user.User;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.api.SnapshotRepository;
import infrastructure.repository.api.UserRepository;
import readmodel.UserDataWithPointsVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PrivateUserDataFinderBean implements PrivateUserDataFinder{

    @EJB
    private UserRepository userRepository;

    @EJB
    private SnapshotHelperFinder snapshotHelperFinder;

    @EJB
    private EventHelperFinder eventHelperFinder;

    @Override
    public UserDataWithPointsVO findDriverDataWithTheirPoints(String pesel, String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPeselAndDrivingLicenseNumber(pesel,drivingLicenseNumber);
        Long userId = user.getAggregateRootEntityId();
        Snapshot snapshot = snapshotHelperFinder.findSnapshotForUser(userId);
        List<Event> events = eventHelperFinder.findUserEvents(userId, snapshot);
        return VisualObjectDataTransfer.transferUserWithPointsToVO(user,snapshot,events);
    }
}
