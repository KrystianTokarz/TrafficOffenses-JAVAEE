package cqrs.commandmodel.manager;

import api.exception.UserNotActiveException;
import api.exception.repository.UserNotFoundException;
import api.querymodel.manager.UserAccountInteface;
import domainmodel.domain.event.Event;
import domainmodel.domain.event.EventItem;
import domainmodel.domain.event.EventItemType;
import domainmodel.domain.event.EventType;
import domainmodel.domain.user.User;
import domainmodel.domain.user.UserRole;
import domainmodel.embaddable.DrivingLicense;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.api.UserRepository;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;

@Stateless
public class UserAccountManager implements UserAccountInteface {

    @EJB
    private UserRepository userRepository;

    @EJB
    private EventRepository eventRepository;
//
//    @EJB
//    CalendarProgTimerDemo calendarProgTimerDemo;

    @Resource
    private TimerService timerService;

    @Resource
    private SessionContext context;

    @Override
    public void addUser(){

        User user = new User("krystian","tokarz","emailos","12");
        user.addUserRole(new UserRole("PUBLIC_USER"));
        user.addDrivingLicense(new DrivingLicense("1234",new Date(), DrivingLicense.DrivingLicenseStatus.ACTIVE));




//        eventRepository.persist(event1);

        User user2 = new User("krystian22222","tokarz2222","emailos","1234567");
        user2.addUserRole(new UserRole("PRIVATE_USER"));
        user2.addUserRole(new UserRole("PUBLIC_USER"));

        userRepository.persist(user);
        Event event1 = new Event(user.generateUserData(), EventType.EVENT1);
        event1.setEventVersion(0l);
        EventItem eventItem1 = new EventItem(EventItemType.EVENT_ITEM_1, 10);
        EventItem eventItem2 = new EventItem(EventItemType.EVENT_ITEM_2, 10);
        event1.addEventItem(eventItem1);
        event1.addEventItem(eventItem2);

        Event event2 = new Event(user.generateUserData(), EventType.EVENT2);
        event2.setEventVersion(0l);
        EventItem eventItem3 = new EventItem(EventItemType.EVENT_ITEM_1, 5);
        EventItem eventItem4 = new EventItem(EventItemType.EVENT_ITEM_2, 3);
        event2.addEventItem(eventItem3);
        event2.addEventItem(eventItem4);

        userRepository.persist(user2);
        eventRepository.persist(event1);
        eventRepository.persist(event2);
//        ScheduleExpression schedule = new ScheduleExpression();
//
//        schedule.hour("*").minute("*").second("13,34,57");

        stop();
//        timerService.createCalendarTimer(schedule);

        System.out.println(timerService.getTimers());


    }

    public void stop() {
        for(Object obj : timerService.getTimers()) {
            Timer t = (Timer) obj;
            t.cancel();
        }
        }


    @Timeout
    public void timer(Timer timer){
        System.out.println("pyk");
    }




}
