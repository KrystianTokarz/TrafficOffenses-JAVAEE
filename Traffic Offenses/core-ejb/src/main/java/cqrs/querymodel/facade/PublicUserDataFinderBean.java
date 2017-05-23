package cqrs.querymodel.facade;

import api.exception.UserNotActiveException;
import api.exception.repository.EventsNotFoundException;
import api.exception.repository.EventsWithVersionNotFoundException;
import api.exception.repository.SnapshotNotFoundException;
import api.exception.repository.UserNotFoundException;
import api.readmodel.PublicUserDataFinder;
import cqrs.querymodel.helper.EventHelperFinder;
import cqrs.querymodel.helper.SnapshotHelperFinder;
import cqrs.querymodel.utility.VisualObjectDataTransfer;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotEntity;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.api.SnapshotRepository;
import readmodel.SimpleUserDataVO;
import domainmodel.domain.user.User;
import infrastructure.repository.api.UserRepository;
import readmodel.UserEventsWithPointsVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

@Stateless
public class PublicUserDataFinderBean implements PublicUserDataFinder {

    @EJB
    private UserRepository userRepository;

    @EJB
    private SnapshotHelperFinder snapshotHelperFinder;

    @EJB
    private EventHelperFinder eventHelperFinder;


    @Override
    public SimpleUserDataVO findSimpleUserData(String pesel) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPesel(pesel);

        return VisualObjectDataTransfer.transferUserToVO(user);
    }


    @Override
    public UserEventsWithPointsVO findUserEventsWithPoints(String pesel) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPesel(pesel);
        Long userId = user.getAggregateRootEntityId();
        Snapshot snapshot = snapshotHelperFinder.findSnapshotForUser(userId);
        List<Event> events = eventHelperFinder.findUserEvents(userId, snapshot);
        return VisualObjectDataTransfer.transferEventsToVO(events,snapshot);


    }

}
