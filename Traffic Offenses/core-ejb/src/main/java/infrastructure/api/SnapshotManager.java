package infrastructure.api;

import domainmodel.domain.snapshot.Snapshot;

import javax.ejb.Local;

@Local
public interface SnapshotManager {

    Snapshot findLastSnapshot(Long userId);

    Snapshot findSnapshotWithVersion(Long userId, Long version);
}
