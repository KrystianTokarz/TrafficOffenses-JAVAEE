package infrastructure.creation.domainmodel.snapshot;

import api.exception.BuilderOperationException;
import domainmodel.domain.snapshot.SnapshotEntity;
import domainmodel.domain.user.User;

public class DefaultSnapshotBuilder {

    private SnapshotEntity snapshot;

    public DefaultSnapshotBuilder(User user, Long eventVersion, Integer numberOfPoints) throws BuilderOperationException {

        if(!user.isActive())
            throw new BuilderOperationException(user.getAggregateRootEntityId() + " client can not create snapshot");
        snapshot = new SnapshotEntity(numberOfPoints, eventVersion, user.generateUserData());

    }

    public SnapshotEntity build(){
        return this.snapshot;
    }

}

