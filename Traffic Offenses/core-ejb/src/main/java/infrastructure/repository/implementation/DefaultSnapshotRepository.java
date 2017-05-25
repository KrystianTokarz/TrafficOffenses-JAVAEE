package infrastructure.repository.implementation;


import api.exception.repository.snapshot.SnapshotNotActiveException;
import api.exception.repository.snapshot.SnapshotNotFoundException;
import api.exception.repository.snapshot.SnapshotWithVersionNotActiveException;
import api.exception.repository.snapshot.SnapshotWithVersionNotFoundException;
import domainmodel.domain.snapshot.Snapshot;
import infrastructure.repository.api.SnapshotRepository;
import infrastructure.repository.GenericRepository;
import domainmodel.domain.snapshot.SnapshotEntity;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class DefaultSnapshotRepository extends GenericRepository<SnapshotEntity> implements SnapshotRepository {


    public Snapshot findLastSnapshot(Long userId) throws SnapshotNotFoundException,
                                                         SnapshotNotActiveException {

        List<Snapshot> snapshot = entityManager.createNamedQuery("Snapshot.findLastSnapshot", Snapshot.class)
                .setParameter("userID", userId)
                .setMaxResults(1)
                .getResultList();

        if(snapshot.isEmpty()) {
            throw new SnapshotNotFoundException("Snapshot for user with id =  " + userId + " is not found");
        }else if(!snapshot.get(0).isActive()) {
            throw new SnapshotNotActiveException("Snapshot for user with id =  " + userId + " is not active");
        }
        return snapshot.get(0);
    }

    public Snapshot findSnapshotWithEventVersion(Long userId, Long version) throws SnapshotWithVersionNotFoundException,
                                                                                     SnapshotWithVersionNotActiveException {

        List<Snapshot> snapshot = entityManager.createNamedQuery("Snapshot.findSnapshotWithVersion", Snapshot.class)
                .setParameter("userID", userId)
                .setParameter("version",version)
                .getResultList();

        if(snapshot.isEmpty()) {
            throw new SnapshotWithVersionNotFoundException("Snapshot for user with id =  " + userId + " is not found");
        }else if(!snapshot.get(0).isActive()) {
            throw new SnapshotWithVersionNotActiveException("Snapshot for user with id =  " + userId + " is not active");
        }
        return snapshot.get(0);
    }

}
