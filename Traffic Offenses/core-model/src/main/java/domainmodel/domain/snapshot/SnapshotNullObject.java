package domainmodel.domain.snapshot;

import domainmodel.embaddable.UserData;

public class SnapshotNullObject implements Snapshot {

    @Override
    public Integer getNumberOfPoints() {
        return 0;
    }

    @Override
    public Long getEventVersion() {
        return 0l;
    }

    @Override
    public UserData getUserData() {
        return null;
    }

    @Override
    public Long getAggregateRootEntityId() {
        return null;
    }

    @Override
    public void setAggregateAsRemoved() {

    }

    @Override
    public boolean isActive() {
        return false;
    }
}
