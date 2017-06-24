package cqrs.command.api;

import domainmodel.domain.event.Event;

import javax.ejb.Local;

@Local
public interface MandateEliminatorManager {
    void generateTimer(Event event);
}
