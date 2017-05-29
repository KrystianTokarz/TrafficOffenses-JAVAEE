package cqrs.query.finder.helper;

import api.exception.repository.snapshot.SnapshotNotFoundException;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotEntity;
import domainmodel.domain.snapshot.SnapshotNullObject;
import infrastructure.repository.implementation.DefaultSnapshotRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SnapshotHelperFinderBeanTest {

    @Mock
    private DefaultSnapshotRepository snapshotRepositoryMock;

    @InjectMocks
    private SnapshotHelperFinderBean sut;


    @Test
    public void shouldFindSnapshotForUser() throws Exception{

        Long userId = 1l;
        Snapshot snapshot = new SnapshotEntity();
        //when
        when(snapshotRepositoryMock.findLastSnapshot(anyLong())).thenReturn(snapshot);
        //then
        Snapshot snapshotForUser = sut.findSnapshotForUser(userId);
        //verify
        assertNotNull(snapshotForUser);
        assertThat(snapshotForUser).isOfAnyClassIn(SnapshotEntity.class);
    }

    @Test
    public void shouldNotFindSnapshotForUser() throws Exception{

        Long userId = 1l;

        //when
        when(snapshotRepositoryMock.findLastSnapshot(anyLong())).thenThrow(SnapshotNotFoundException.class);
        //then
        Snapshot snapshotForUser = sut.findSnapshotForUser(userId);
        //verify
        assertNotNull(snapshotForUser);
        assertThat(snapshotForUser).isOfAnyClassIn(SnapshotNullObject.class);
    }
}