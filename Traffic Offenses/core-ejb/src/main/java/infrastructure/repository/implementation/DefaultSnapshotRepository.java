package infrastructure.repository.implementation;


import api.exception.repository.SnapshotNotFoundException;
import api.exception.repository.SnapshotWithVersionNotFoundException;
import infrastructure.repository.api.SnapshotRepository;
import infrastructure.repository.GenericRepository;
import domainmodel.domain.snapshot.SnapshotEntity;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

@Stateless
public class DefaultSnapshotRepository extends GenericRepository<SnapshotEntity> implements SnapshotRepository {


    public SnapshotEntity findLastSnapshot(Long userId) throws SnapshotNotFoundException {
        try {
            SnapshotEntity snapshot = entityManager.createNamedQuery("Snapshot.findLastSnapshot", SnapshotEntity.class)
                    .setParameter("userID", userId)
                    .setMaxResults(1)
                    .getSingleResult();
            return snapshot;
        }catch (NoResultException e) {
            throw new SnapshotNotFoundException("User " + userId + " does not have snapshot");
        }



    }

    public SnapshotEntity findSnapshotWithEventVersion(Long userId, String version) throws SnapshotWithVersionNotFoundException {
        SnapshotEntity snapshot = entityManager.createNamedQuery("Snapshot.findSnapshotWithVersion", SnapshotEntity.class)
                .setParameter("userID", userId)
                .setParameter("version",version)
                .setMaxResults(1)
                .getSingleResult();

        if(snapshot == null)
            throw new SnapshotWithVersionNotFoundException("User " + userId + " does not have snapshot with version = " + version);

        return snapshot;
    }

}
