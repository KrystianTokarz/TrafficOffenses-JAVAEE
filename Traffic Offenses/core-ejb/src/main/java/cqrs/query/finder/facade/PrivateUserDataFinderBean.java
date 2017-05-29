package cqrs.query.finder.facade;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.PrivateUserDataFinder;
import cqrs.query.api.EventHelperFinder;
import cqrs.query.api.SnapshotHelperFinder;
import cqrs.query.api.VisualObjectDataTransfer;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.user.User;
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

    @EJB
    private VisualObjectDataTransfer visualObjectDataTransfer;

    @Override
    public UserDataWithPointsVO findDriverDataWithTheirPoints(String pesel, String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPeselAndDrivingLicenseNumber(pesel,drivingLicenseNumber);
        Long userId = user.getAggregateRootEntityId();
        Snapshot snapshot = snapshotHelperFinder.findSnapshotForUser(userId);
        List<Event> events = eventHelperFinder.findUserEvents(userId, snapshot);
        return visualObjectDataTransfer.transferUserWithPointsToVO(user,snapshot,events);
    }
}
