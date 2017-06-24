import api.commandmodel.AsynchronousCommandFacade;
//import domainmodel.suport.MandatePosition;
import infrastructure.api.EmailManager;
import cqrs.command.api.ValidatorResolver;
import domainmodel.domain.event.Event;
import domainmodel.domain.event.EventItem;
import domainmodel.domain.event.EventItemType;
import domainmodel.domain.event.EventType;

import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;
import infrastructure.api.PdfManager;
import infrastructure.repository.api.EventRepository;
//import infrastructure.repository.api.RoadOffensesDictionaryRepository;
import infrastructure.repository.api.UserRepository;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.Date;

@Startup
@Singleton
public class StartupContext {

    @EJB
    private UserRepository userRepository;

    @EJB
    private EventRepository eventRepository;


    @Inject
    private Logger logger;

    @Resource
    private TimerService timerService;


    @EJB
    private ValidatorResolver validatorResolver;

    @EJB
    private AsynchronousCommandFacade commandFacade;

    @Inject
    private EmailManager mailSenderBean;

    @EJB
    private PdfManager pdfManager;

    @PostConstruct
    public void addUser(){

        User user = new User.UserBuilder("Krystian","Tokarz","krystiantokarz@wp.pl","95041302934")
                .drivingLicense(new DrivingLicense("11111/22/3333",new Date(), DrivingLicense.DrivingLicenseStatus.ACTIVE))
                .build();


        User user2 = new User.UserBuilder("Juzek","Burczymucha","emailos@onet.pl","95041302931")
//                .drivingLicense(new DrivingLicense("12345/123/1234",new Date(), DrivingLicense.DrivingLicenseStatus.ACTIVE))
                .privateRole("haslo")
                .build();

        User user3 = new User.UserBuilder("Adrian","Nowak","mail@gmail.com","95041302932")
                .drivingLicense(new DrivingLicense("00000/00/0000",new Date(), DrivingLicense.DrivingLicenseStatus.ACTIVE))
                .administratorRole("haslo")
                .build();
        userRepository.persist(user);
        userRepository.persist(user2);
        userRepository.persist(user3);

        Event event1 = new Event(user.generateUserData(), EventType.MANDATE,0l);
        EventItem eventItem1 = new EventItem(EventItemType.SPEED_LIMIT_10, 100,EventItemType.SPEED_LIMIT_10.getPoints());
        EventItem eventItem2 = new EventItem(EventItemType.CAR_BUMP, 200,EventItemType.CAR_BUMP.getPoints());
        event1.addEventItem(eventItem1);
        event1.addEventItem(eventItem2);

        Event event2 = new Event(user.generateUserData(), EventType.MANDATE,0l);
        EventItem eventItem3 = new EventItem(EventItemType.SPEED_LIMIT_10, 100,EventItemType.SPEED_LIMIT_10.getPoints());
        EventItem eventItem4 = new EventItem(EventItemType.SPEED_LIMIT_20, 100,EventItemType.SPEED_LIMIT_20.getPoints());
        event2.addEventItem(eventItem3);
        event2.addEventItem(eventItem4);



        eventRepository.persist(event1);
        eventRepository.persist(event2);

        stop();

    }

    public void stop() {
        for(Object obj : timerService.getTimers()) {
            Timer t = (Timer) obj;
            t.cancel();
            }
        }


}
