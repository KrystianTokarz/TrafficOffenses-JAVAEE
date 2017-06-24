package cqrs.command.handler;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import cqrs.command.api.MandateEliminatorManager;
import domainmodel.embaddable.UserData;
import infrastructure.api.DrivingLicenseStatusManager;
import infrastructure.api.EmailManager;
import cqrs.command.api.AsynchronousCommandHandler;
import domainmodel.domain.event.Event;
import domainmodel.domain.event.EventItem;
import domainmodel.domain.event.EventType;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.user.User;
import infrastructure.annotations.Handler;
import infrastructure.api.SnapshotManager;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.api.UserRepository;
import org.slf4j.Logger;
import writemodel.CreateMandateCommand;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.List;

@Handler(name = "CreateMandateCommand")
@Stateless
public class CreateMandateCommandHandler implements AsynchronousCommandHandler<CreateMandateCommand> {

    @EJB
    private UserRepository userRepository;

    @EJB
    private EventRepository eventRepository;

    @EJB
    private MandateEliminatorManager mandateEliminatorManager;

    @EJB
    private EmailManager mailSender;

    @EJB
    private SnapshotManager snapshotManager;

    @EJB
    private DrivingLicenseStatusManager drivingLicenseStatusManager;

    @Inject
    private Logger logger;

    @Override
    public void handle(CreateMandateCommand command) {
        try {
            User user = userRepository.findUserByDrivingLicenseNumber(command.getDrivingLicenseNumber());
            createMandate(command,user);
        } catch (UserNotFoundException e) {
            logger.error("CreateMandateCommandHandler - User not found");
            e.printStackTrace();
        } catch (UserNotActiveException e) {
            logger.error("CreateMandateCommandHandler - User not active");
            e.printStackTrace();
        }

    }

    private void createMandate(CreateMandateCommand command, User user){
        Snapshot snapshot = snapshotManager.findLastSnapshot(user.getAggregateRootEntityId());

        Event event = createMandateEvent(user.generateUserData(),
                command.getMandatePositions(),
                snapshot.getEventVersion()
        );

        eventRepository.persist(event);
        checkDrivingLicenseStatus(event, snapshot, user);
        mailSender.sendEmail(event);
    }

    private Event createMandateEvent(UserData userData,List<EventItem> eventItems, Long version){
        Event event = new Event(userData, EventType.MANDATE, version);
        for (EventItem eventItem: eventItems) {
            event.addEventItem(eventItem);
        }
        return event;
    }

    private void checkDrivingLicenseStatus(Event event, Snapshot snapshot, User user){
        if(drivingLicenseStatusManager.checkDrivingLicenseIsStillActive(snapshot,user)){
            generateTimer(event);
        }else{
            changeLicenseStatus(user);
        }
    }

    private void generateTimer(Event event){
        mandateEliminatorManager.generateTimer(event);
    }

    private void changeLicenseStatus(User user){
        drivingLicenseStatusManager.changeLicenseStatusToInactive(user);
    }


}
