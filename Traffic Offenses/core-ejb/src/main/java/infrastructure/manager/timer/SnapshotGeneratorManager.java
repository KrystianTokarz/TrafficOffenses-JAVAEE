package infrastructure.manager.timer;

import api.exception.repository.event.EventsWithVersionNotFoundException;
import api.exception.repository.snapshot.SnapshotNotActiveException;
import api.exception.repository.snapshot.SnapshotNotFoundException;
import domainmodel.embaddable.DrivingLicense;
import exception.NullDrivingLicenseException;
import infrastructure.api.EventManager;
import infrastructure.api.PointsMandateCalculator;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotEntity;
import domainmodel.domain.snapshot.SnapshotNullObject;
import domainmodel.domain.user.User;
import infrastructure.api.SnapshotManager;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.api.SnapshotRepository;
import infrastructure.repository.api.UserRepository;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

@Singleton
@LocalBean
public class SnapshotGeneratorManager{

    @EJB
    private UserRepository userRepository;

    @EJB
    private SnapshotRepository snapshotRepository;

    @EJB
    private SnapshotManager snapshotManager;

    @EJB
    private EventManager eventManager;

    @Inject
    private PointsMandateCalculator pointsCalculator;


//    @Schedule(month = "0", persistent = false)
    @Schedule( hour = "*", minute = "1", persistent = false)
    public void snapshotSchedule() {
        generateSnapshotWithNewVersion();
    }


    private void generateSnapshotWithNewVersion(){
        List<User> activeUsersInSystem = userRepository.findAllActiveDrivers();
        for (User user: activeUsersInSystem) {
            if(checkUserDrivingLicenseIsActive(user))
                generateSnapshotForSelectedUser(user);
        }
    }

    private void generateSnapshotForSelectedUser(User user){
        List<Event> events = eventManager.findEventsForLastSnapshot(user.getAggregateRootEntityId());
        if(!events.isEmpty()){
            SnapshotEntity snapshotEntity = createNewSnapshot(events, user);
            snapshotRepository.persist(snapshotEntity);
        }
    }

    private SnapshotEntity createNewSnapshot(List<Event> events, User user){
        Snapshot snapshot = snapshotManager.findLastSnapshot(user.getAggregateRootEntityId());
        int numberOfPoints = pointsCalculator.calculateUserPoints(events,snapshot);
        Long newEventVersion = generateNewEventVersion(snapshot);
        return  new SnapshotEntity(numberOfPoints, newEventVersion, user.generateUserData());
    }

    private Long generateNewEventVersion(Snapshot snapshot){
        Long oldVersion = snapshot.getEventVersion();
        return  oldVersion + 1;
    }

    private boolean checkUserDrivingLicenseIsActive(User user){
        boolean result = false;
        try {
            DrivingLicense.DrivingLicenseStatus status = user.getDrivingLicense().getStatus();
            result  = (status.equals(DrivingLicense.DrivingLicenseStatus.ACTIVE));
        } catch (NullDrivingLicenseException e) {
            e.printStackTrace();
        }
        return result;
    }

}
