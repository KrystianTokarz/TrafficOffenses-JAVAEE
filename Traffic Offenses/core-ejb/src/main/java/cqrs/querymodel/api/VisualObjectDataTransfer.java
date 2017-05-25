package cqrs.querymodel.api;

import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.user.User;
import readmodel.SimpleUserDataVO;
import readmodel.UserDataWithPointsVO;
import readmodel.UserEventsWithPointsVO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface VisualObjectDataTransfer {
    SimpleUserDataVO transferUserToVO(User user);

    UserEventsWithPointsVO transferEventsToVO(List<Event> events, Snapshot snapshot);

    UserDataWithPointsVO transferUserWithPointsToVO(User user, Snapshot snapshot, List<Event> events);
}
