package infrastructure.creation.domainmodel.event;


import api.exception.BuilderOperationException;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.SnapshotEntity;
import domainmodel.domain.user.User;

import javax.enterprise.context.Dependent;

@Dependent
public class DefaultEventFactory {

    public Event create(User user, SnapshotEntity snapshot) throws BuilderOperationException {

        if(!user.isActive())
            throw new BuilderOperationException(user.getAggregateRootEntityId() + " client can not create event");

//        Event event = new Event(new Date(), user.generateUserData());

        return null;
    }
}
