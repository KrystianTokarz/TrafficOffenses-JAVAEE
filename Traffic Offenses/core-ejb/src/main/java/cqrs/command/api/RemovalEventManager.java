package cqrs.command.api;

import javax.ejb.Local;

@Local
public interface RemovalEventManager {
    void createEventToDeletePointsAfterYear(Long eventId);
}
