package cqrs.querymodel.utility;

import cqrs.querymodel.api.PointsCalculator;
import cqrs.querymodel.api.VisualObjectDataTransfer;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import readmodel.SimpleUserDataVO;
import domainmodel.domain.user.User;
import readmodel.UserDataWithPointsVO;
import readmodel.UserEventsWithPointsVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.awt.*;
import java.util.List;

@Stateless
public class VisualObjectDataTransferBean implements VisualObjectDataTransfer {

    @EJB
    private PointsCalculator pointsCalculator;

    public VisualObjectDataTransferBean() {
    }

    @Override
    public SimpleUserDataVO transferUserToVO(User user){
        return new SimpleUserDataVO(user);
    }

    @Override
    public  UserEventsWithPointsVO transferEventsToVO(List<Event> events, Snapshot snapshot){
        int points = 0;
        if(snapshot == null)
            points = pointsCalculator.calculateUserPoints(events, 0);
        else
            points = pointsCalculator.calculateUserPoints(events, snapshot.getNumberOfPoints());
        return new UserEventsWithPointsVO(events, points);
    }


    @Override
    public  UserDataWithPointsVO transferUserWithPointsToVO(User user, Snapshot snapshot, List<Event> events){
        int points = 0;
        if(snapshot == null)
            points = pointsCalculator.calculateUserPoints(events, 0);
        else
            points = pointsCalculator.calculateUserPoints(events, snapshot.getNumberOfPoints());
        return new UserDataWithPointsVO(user, points);

    }


}
