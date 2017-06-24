package cqrs.query.finder;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.PublicUserDataFinder;

import cqrs.query.api.VisualObjectDataTransfer;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import infrastructure.api.EventManager;
import infrastructure.api.SnapshotManager;
import readmodel.SimpleUserDataVO;
import domainmodel.domain.user.User;
import infrastructure.repository.api.UserRepository;
import readmodel.UserEventsWithPointsVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class PublicUserDataFinderBean implements PublicUserDataFinder {

    @EJB
    private UserRepository userRepository;

    @EJB
    private VisualObjectDataTransfer visualObjectDataTransfer;

    @EJB
    private EventManager eventManager;

    @EJB
    private SnapshotManager snapshotManager;

    @Override
    public SimpleUserDataVO findSimpleUserData(String pesel) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPesel(pesel);
        return visualObjectDataTransfer.transferUserToVO(user);
    }

    @Override
    public UserEventsWithPointsVO findUserEvents(String pesel) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPesel(pesel);
        List<Event> events = eventManager.findEventsForLastSnapshot(user.getAggregateRootEntityId());
        Snapshot snapshot = snapshotManager.findLastSnapshot(user.getAggregateRootEntityId());
        return visualObjectDataTransfer.transferEventsToVO(events,snapshot);
    }

    @Override
    public UserEventsWithPointsVO findEventsAfterSelectedVersion(String pesel, Long version) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPesel(pesel);
        Snapshot snapshot = snapshotManager.findSnapshotWithVersion(user.getAggregateRootEntityId(),version);
        List<Event> events = eventManager.findEventsWithAndAfterSelectedVersion(user.getAggregateRootEntityId(),snapshot.getEventVersion());
        return visualObjectDataTransfer.transferEventsToVO(events,snapshot);
    }
}
