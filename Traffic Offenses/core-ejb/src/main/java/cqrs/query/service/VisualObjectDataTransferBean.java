package cqrs.query.service;

import infrastructure.api.PointsMandateCalculator;
import cqrs.query.api.VisualObjectDataTransfer;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import readmodel.SimpleUserDataVO;
import domainmodel.domain.user.User;
import readmodel.UserDataWithMissingRolesVO;
import readmodel.UserDataWithPointsVO;
import readmodel.UserEventsWithPointsVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class VisualObjectDataTransferBean implements VisualObjectDataTransfer {

    @EJB
    private PointsMandateCalculator pointsCalculator;

    @Override
    public SimpleUserDataVO transferUserToVO(User user){
        return new SimpleUserDataVO(user);
    }

    @Override
    public  UserEventsWithPointsVO transferEventsToVO(List<Event> events, Snapshot snapshot){
        int points = calculateActuallyUserPoints(events, snapshot);
        return new UserEventsWithPointsVO(events, points,snapshot.getEventVersion());
    }

    @Override
    public  UserDataWithPointsVO transferUserWithPointsToVO(User user, Snapshot snapshot, List<Event> events){
        int points = calculateActuallyUserPoints(events, snapshot);
        return new UserDataWithPointsVO(user, points);
    }

    @Override
    public UserDataWithMissingRolesVO transferUserWithMissingRolesToVO(User user, List<String> userRoles){
        return new UserDataWithMissingRolesVO(user, userRoles);
    }


    private int calculateActuallyUserPoints(List<Event> events, Snapshot snapshot){
        return pointsCalculator.calculateUserPoints(events,snapshot);
    }


}
