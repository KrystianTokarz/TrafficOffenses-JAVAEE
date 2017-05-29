package cqrs.query.api;

import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EventHelperFinder {
    List<Event> findUserEvents(Long userId, Snapshot snapshot);
}
