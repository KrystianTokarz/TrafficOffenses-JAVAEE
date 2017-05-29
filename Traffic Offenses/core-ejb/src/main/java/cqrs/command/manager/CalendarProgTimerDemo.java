//package cqrs.commandmodel.manager;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.ejb.*;
//import java.util.Collection;
//import java.util.Date;
//
//@Singleton
//@LocalBean
//@Startup
//public class CalendarProgTimerDemo {
//
//    @Resource
//    private TimerService timerService;
//
//    @PostConstruct
//    private void init() {
//
//
//        TimerConfig timerConfig = new TimerConfig();
//
//        timerConfig.setInfo("CalendarProgTimerDemo_Info");
//
//
//        ScheduleExpression schedule = new ScheduleExpression();
//
//        schedule.hour("*").minute("*").second("13,34,57");
//
//        timerService.createCalendarTimer(schedule);
//        }
//
//
//
//    @Timeout
//    public void execute(Timer timer) {
//        System.out.println( timerService.getAllTimers().size());
//
//        System.out.println("WITAM");
//
//    }
//
//
//
//}
