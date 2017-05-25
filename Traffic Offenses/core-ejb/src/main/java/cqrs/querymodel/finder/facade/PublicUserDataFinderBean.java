package cqrs.querymodel.finder.facade;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.readmodel.PublicUserDataFinder;
import cqrs.querymodel.api.EventHelperFinder;
import cqrs.querymodel.api.SnapshotHelperFinder;

import cqrs.querymodel.api.VisualObjectDataTransfer;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
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
    private SnapshotHelperFinder snapshotHelperFinder;

    @EJB
    private EventHelperFinder eventHelperFinder;

    @EJB
    private VisualObjectDataTransfer visualObjectDataTransfer;


    @Override
    public SimpleUserDataVO findSimpleUserData(String pesel) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPesel(pesel);

        return visualObjectDataTransfer.transferUserToVO(user);
    }


    @Override
    public UserEventsWithPointsVO findUserEventsWithPoints(String pesel) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPesel(pesel);
        Long userId = user.getAggregateRootEntityId();
        Snapshot snapshot = snapshotHelperFinder.findSnapshotForUser(userId);
        List<Event> events = eventHelperFinder.findUserEvents(userId, snapshot);
        return visualObjectDataTransfer.transferEventsToVO(events,snapshot);


    }

}
