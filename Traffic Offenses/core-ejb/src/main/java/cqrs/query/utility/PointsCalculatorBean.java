package cqrs.query.utility;

import cqrs.query.api.PointsCalculator;
import domainmodel.domain.event.Event;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

@Stateless
public class PointsCalculatorBean  implements PointsCalculator{


    public PointsCalculatorBean() {
    }

    private List<Event> safe(List<Event> events ) {
        return events == null ? Collections.EMPTY_LIST : events;
    }


    @Override
    public int calculateUserPoints(List<Event> events, int startedNumberOfPoints){

        for (Event event: safe(events)) {
            startedNumberOfPoints += event.getEventItemList()
                    .stream()
                    .mapToInt(e -> e.getPoints()).sum();
        }
        return startedNumberOfPoints;
    }
}
