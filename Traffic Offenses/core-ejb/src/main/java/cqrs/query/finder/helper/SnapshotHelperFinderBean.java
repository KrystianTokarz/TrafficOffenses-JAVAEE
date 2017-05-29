package cqrs.query.finder.helper;

import api.exception.repository.snapshot.SnapshotNotActiveException;
import api.exception.repository.snapshot.SnapshotNotFoundException;
import cqrs.query.api.SnapshotHelperFinder;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotNullObject;
import infrastructure.repository.api.SnapshotRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SnapshotHelperFinderBean implements SnapshotHelperFinder {

    @EJB
    private SnapshotRepository snapshotRepository;


    @Override
    public Snapshot findSnapshotForUser(Long userId){
        try {
            return snapshotRepository.findLastSnapshot(userId);
        } catch (SnapshotNotFoundException | SnapshotNotActiveException e ) {
            return new SnapshotNullObject();
        }

    }
}
