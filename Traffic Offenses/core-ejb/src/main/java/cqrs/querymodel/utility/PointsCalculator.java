package cqrs.querymodel.utility;

import domainmodel.domain.event.Event;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import java.util.List;

public final class PointsCalculator {

    public static int calculateUserPoints(List<Event> events, int startedNumberOfPoints){


        for (Event event: events) {
            startedNumberOfPoints += event.getEventItemList()
                    .stream()
                    .mapToInt(e -> e.getPoints()).sum();
        }
        return startedNumberOfPoints;
    }
}
