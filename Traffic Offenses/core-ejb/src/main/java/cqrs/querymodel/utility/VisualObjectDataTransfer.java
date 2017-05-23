package cqrs.querymodel.utility;

import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotEntity;
import readmodel.SimpleUserDataVO;
import domainmodel.domain.user.User;
import readmodel.UserDataWithPointsVO;
import readmodel.UserEventsWithPointsVO;

import java.util.List;


public final class VisualObjectDataTransfer {


    public static SimpleUserDataVO transferUserToVO(User user){
        return new SimpleUserDataVO(user);
    }

    public static UserEventsWithPointsVO transferEventsToVO(List<Event> events, Snapshot snapshot){
        int points = PointsCalculator.calculateUserPoints(events, snapshot.getNumberOfPoints());
        return new UserEventsWithPointsVO(events, points);
    }


    public static UserDataWithPointsVO transferUserWithPointsToVO(User user, Snapshot snapshot, List<Event> events){
        Integer numberPointsInLastSnapshot = snapshot.getNumberOfPoints();
        int points = PointsCalculator.calculateUserPoints(events,numberPointsInLastSnapshot);

        return new UserDataWithPointsVO(user, points);

    }


}
