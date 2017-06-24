package infrastructure.manager;

import domainmodel.domain.snapshot.Snapshot;
import infrastructure.api.PointsMandateCalculator;
import domainmodel.domain.event.Event;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;

@Stateless
public class PointsMandateCalculatorBean implements PointsMandateCalculator {


    private List<Event> safe(List<Event> events ) {
        return events == null ? Collections.EMPTY_LIST : events;
    }


    @Override
    public int calculateUserPoints(List<Event> events, Snapshot snapshot){
        int startedNumberOfPoints = snapshot.getNumberOfPoints();

        for (Event event: safe(events)) {
            startedNumberOfPoints += event.getEventItemList()
                        .stream()
                        .mapToInt(e -> e.getPoints()).sum();
        }
        return startedNumberOfPoints;
    }

    @Override
    public int calculateUserPoints(Event event){

        return event.getEventItemList()
                        .stream()
                        .mapToInt(e -> e.getPoints()).sum();

    }

}
