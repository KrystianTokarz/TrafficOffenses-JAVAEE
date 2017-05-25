package infrastructure.repository.api;

import api.exception.repository.*;
import api.exception.repository.snapshot.SnapshotNotActiveException;
import api.exception.repository.snapshot.SnapshotNotFoundException;
import api.exception.repository.snapshot.SnapshotWithVersionNotActiveException;
import api.exception.repository.snapshot.SnapshotWithVersionNotFoundException;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotEntity;

import javax.ejb.Local;

@Local
public interface SnapshotRepository {

    Snapshot load(Long aggregateId) throws AggregateNotFoundException, AggregateNotActiveException;

    void persist(SnapshotEntity snapshot);

    Snapshot merge(SnapshotEntity snapshot);

    void delete(Long aggregateId) throws AggregateNotFoundException, AggregateNotActiveException;

    Snapshot findLastSnapshot(Long userId) throws SnapshotNotFoundException, SnapshotNotActiveException;

    Snapshot findSnapshotWithEventVersion(Long userId, Long version) throws SnapshotWithVersionNotFoundException, SnapshotWithVersionNotActiveException;


}
