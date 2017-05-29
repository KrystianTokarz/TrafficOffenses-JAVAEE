package cqrs.query.api;

import domainmodel.domain.event.Event;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PointsCalculator {
    int calculateUserPoints(List<Event> events, int startedNumberOfPoints);
}
