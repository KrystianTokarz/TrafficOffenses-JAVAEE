package cqrs.querymodel.utility;

import cqrs.querymodel.api.PointsCalculator;
import domainmodel.domain.event.Event;
import domainmodel.domain.event.EventItem;
import domainmodel.domain.event.EventItemType;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class PointsCalculatorBeanTest {


    private static  PointsCalculator sut;

    @BeforeClass
    public static void setUp(){
        sut = new PointsCalculatorBean();
    }


    @Test
    public void shouldCalculateUserPoints() throws Exception {

        List<Event> eventList = new ArrayList<>();
        Event event = new Event();
        event.addEventItem(new EventItem(null, 5));
        eventList.add(event);


        int result = sut.calculateUserPoints(eventList, 5);

        assertThat(result).isEqualTo(10);
    }

    @Test
    public void shouldCalculateUserPointsWithoutEventList() throws Exception {

        int result = sut.calculateUserPoints(null, 5);

        assertThat(result).isEqualTo(5);
    }




}