package infrastructure.manager;

import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;
import exception.NullDrivingLicenseException;
import infrastructure.api.DrivingLicenseStatusManager;
import infrastructure.api.EventManager;
import infrastructure.api.PointsMandateCalculator;
import infrastructure.repository.api.UserRepository;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class DrivingLicenseStatusManagerBean implements DrivingLicenseStatusManager {

    @EJB
    private EventManager eventManager;

    @EJB
    private PointsMandateCalculator pointsMandateCalculator;

    @EJB
    private UserRepository userRepository;

    @Inject
    private Logger logger;

    public boolean checkDrivingLicenseIsStillActive(Snapshot snapshot,User user){
        List<Event> events = eventManager.findEventsForLastSnapshot(user.getAggregateRootEntityId());
        int numberOfPoints = pointsMandateCalculator.calculateUserPoints(events, snapshot);
        return !(numberOfPoints > 25);
    }

    public void changeLicenseStatusToInactive(User user){
        try{
            user.changeLicenseDrivingStatus(DrivingLicense.DrivingLicenseStatus.TEMPORARY_INACTIVE);
            userRepository.merge(user);
        } catch (NullDrivingLicenseException e) {
            logger.debug("Change license status failed - driving license not found");
            e.printStackTrace();
        }


    }
}
