package infrastructure.manager;

import api.exception.repository.snapshot.SnapshotNotFoundException;
import api.exception.repository.snapshot.SnapshotWithVersionNotActiveException;
import api.exception.repository.snapshot.SnapshotWithVersionNotFoundException;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotNullObject;
import infrastructure.api.SnapshotManager;
import infrastructure.repository.api.SnapshotRepository;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SnapshotManagerBean implements SnapshotManager {

    @EJB
    private SnapshotRepository snapshotRepository;

    @Inject
    private Logger logger;

    @Override
    public Snapshot findLastSnapshot(Long userId){
        try {
            return snapshotRepository.findLastSnapshot(userId);
        } catch (SnapshotNotFoundException e) {
            logger.debug("Last snapshot not found exception");
            return new SnapshotNullObject();
        }
    }

    @Override
    public Snapshot findSnapshotWithVersion(Long userId, Long version){
        try {
            return snapshotRepository.findSnapshotWithEventVersion(userId,version);
        } catch (SnapshotWithVersionNotFoundException e1) {
            logger.debug("Snapshot with selected version = {0} not found exception",version);
            return new SnapshotNullObject();
        } catch (SnapshotWithVersionNotActiveException e1) {
            logger.debug("Snapshot with selected version = {0} is not active",version);
            return findSnapshotWithVersion(userId, version--);
        }

    }

}
