package cqrs.querymodel.helper;

import api.exception.repository.SnapshotNotFoundException;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotEntity;
import domainmodel.domain.snapshot.SnapshotNullObject;
import infrastructure.repository.api.SnapshotRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SnapshotHelperFinder {

    @EJB
    private SnapshotRepository snapshotRepository;


    public Snapshot findSnapshotForUser(Long userId){
        try {
            return snapshotRepository.findLastSnapshot(userId);
        } catch (SnapshotNotFoundException e) {
            return new SnapshotNullObject();
        }
    }
}
