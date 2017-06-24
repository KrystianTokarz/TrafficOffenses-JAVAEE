package cqrs.query.finder;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.PrivateUserDataFinder;
import cqrs.query.api.VisualObjectDataTransfer;
import domainmodel.domain.event.Event;
import domainmodel.domain.event.EventItemType;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.user.User;
import infrastructure.api.EventManager;
import infrastructure.api.OffensesManager;
import infrastructure.api.SnapshotManager;
import infrastructure.repository.api.UserRepository;
import readmodel.UserDataWithPointsVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PrivateUserDataFinderBean implements PrivateUserDataFinder {

    @EJB
    private UserRepository userRepository;

    @EJB
    private EventManager eventManager;

    @EJB
    private SnapshotManager snapshotManager;

    @EJB
    private VisualObjectDataTransfer visualObjectDataTransfer;

    @EJB
    private OffensesManager offensesManager;

    @Override
    public UserDataWithPointsVO findDriverDataWithTheirPointsByPesel(String pesel) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPesel(pesel);
        Long userId = user.getAggregateRootEntityId();
        Snapshot snapshot = snapshotManager.findLastSnapshot(userId);
        List<Event> events = eventManager.findEventsForLastSnapshot(userId);
        return visualObjectDataTransfer.transferUserWithPointsToVO(user,snapshot,events);
    }

    @Override
    public UserDataWithPointsVO findDriverDataWithTheirPointsByDrivingLicense(String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByDrivingLicenseNumber(drivingLicenseNumber);
        Long userId = user.getAggregateRootEntityId();
        Snapshot snapshot = snapshotManager.findLastSnapshot(userId);
        List<Event> events = eventManager.findEventsForLastSnapshot(userId);
        return visualObjectDataTransfer.transferUserWithPointsToVO(user,snapshot,events);
    }

    @Override
    public List<EventItemType> findAllRoadOffenses(){
        return offensesManager.getEventItemTypeList();
    }

}
