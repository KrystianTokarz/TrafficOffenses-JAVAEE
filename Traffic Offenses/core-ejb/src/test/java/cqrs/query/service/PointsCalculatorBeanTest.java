//package cqrs.query.service;
//
//import infrastructure.api.PointsMandateCalculator;
//import infrastructure.manager.PointsMandateCalculatorBean;
//import domainmodel.domain.event.Event;
//import domainmodel.domain.event.EventItem;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class PointsCalculatorBeanTest {
//
//
//    private static PointsMandateCalculator sut;
//
//    @BeforeClass
//    public static void setUp(){
//        sut = new PointsMandateCalculatorBean();
//    }
//
//
//    @Test
//    public void shouldCalculateUserPoints() throws Exception {
//
//        List<Event> eventList = new ArrayList<>();
//        Event event = new Event();
//        event.addEventItem(new EventItem(null, 5,5));
//        eventList.add(event);
//
//
//        int result = sut.calculateUserPoints(eventList, 5);
//
//        assertThat(result).isEqualTo(10);
//    }
//
//    @Test
//    public void shouldCalculateUserPointsWithoutEventList() throws Exception {
//
//        int result = sut.calculateUserPoints(null, 5);
//
//        assertThat(result).isEqualTo(5);
//    }
//
//
//
//
//}