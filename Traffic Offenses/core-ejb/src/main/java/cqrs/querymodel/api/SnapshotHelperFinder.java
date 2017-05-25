package cqrs.querymodel.api;

import domainmodel.domain.snapshot.Snapshot;

import javax.ejb.Local;

@Local
public interface SnapshotHelperFinder {
    Snapshot findSnapshotForUser(Long userId);
}
