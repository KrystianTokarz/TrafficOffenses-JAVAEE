package infrastructure.repository.api;

import api.exception.repository.*;
import domainmodel.domain.snapshot.SnapshotEntity;

import javax.ejb.Local;

@Local
public interface SnapshotRepository {

    SnapshotEntity load(Long aggregateId) throws AggregateNotFoundException, AggregateNotActiveException;

    void persist(SnapshotEntity snapshot);

    SnapshotEntity merge(SnapshotEntity snapshot);

    void delete(Long aggregateId) throws AggregateNotFoundException, AggregateNotActiveException;

    SnapshotEntity findLastSnapshot(Long userId) throws SnapshotNotFoundException;

    public SnapshotEntity findSnapshotWithEventVersion(Long userId, String version) throws SnapshotWithVersionNotFoundException;


}
