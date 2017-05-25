package cqrs.querymodel.finder.facade;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.readmodel.PrivateUserDataFinder;
import cqrs.querymodel.api.EventHelperFinder;
import cqrs.querymodel.api.SnapshotHelperFinder;
import cqrs.querymodel.api.VisualObjectDataTransfer;
import cqrs.querymodel.utility.VisualObjectDataTransferBean;
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
