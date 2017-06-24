package cqrs.query.service;

import domainmodel.domain.snapshot.SnapshotNullObject;
import infrastructure.manager.PointsMandateCalculatorBean;
import domainmodel.domain.event.Event;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotEntity;
import domainmodel.domain.user.User;
import domainmodel.embaddable.UserData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import readmodel.SimpleUserDataVO;
import readmodel.UserDataWithPointsVO;
import readmodel.UserEventsWithPointsVO;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class VisualObjectDataTransferBeanTest {

    @Mock
    private PointsMandateCalculatorBean pointsCalculatorMock;

    @InjectMocks
    private VisualObjectDataTransferBean sut;


//
    @Test
    public void TransferEventsToVO() throws Exception {

        Integer startPoints = 10;
        Snapshot snapshot = new SnapshotEntity(startPoints, 0l, new UserData());
        List<Event> events = new ArrayList<>();


        //given
        given(pointsCalculatorMock.calculateUserPoints(events, snapshot)).willReturn(10);

        //when
        UserEventsWithPointsVO userEventsWithPointsVO = sut.transferEventsToVO(events, snapshot);

        //then
        assertThat(userEventsWithPointsVO.getNumberOfPoints()).isEqualTo(10);
        assertNotNull(userEventsWithPointsVO);

    }

    @Test
    public void shouldTransferEventsToVOWithSnapshotNullObject() throws Exception {

        List<Event> events = new ArrayList<>();

        UserEventsWithPointsVO userEventsWithPointsVO = sut.transferEventsToVO(events, new SnapshotNullObject());

        assertNotNull(userEventsWithPointsVO);

    }

    @Test
    public void TransferEventsToVOToVOWithoutEventsList() throws Exception {

        Snapshot snapshot = new SnapshotEntity(10, 0l, new UserData());

        UserEventsWithPointsVO userEventsWithPointsVO = sut.transferEventsToVO(null, snapshot);

        assertNotNull(userEventsWithPointsVO);

    }

    @Test
    public void TransferEventsToVOToVOWithoutEventsListAndSnapshotNullObject() throws Exception {

        Snapshot snapshot = new SnapshotEntity(10, 0l, new UserData());

        UserEventsWithPointsVO userEventsWithPointsVO = sut.transferEventsToVO(null, new SnapshotNullObject());

        assertNotNull(userEventsWithPointsVO);

    }

    @Test
    public void shouldTransferUserToVO() throws Exception{
        SimpleUserDataVO simpleUserDataVO = sut.transferUserToVO(new User());

        assertNotNull(simpleUserDataVO);
    }

    @Test
    public void transferUserWithPointsToVO() throws Exception{

        List<Event> events = new ArrayList<>();
        Snapshot snapshot = new SnapshotEntity(0, null, null);

        UserDataWithPointsVO userDataWithPointsVO = sut.transferUserWithPointsToVO(new User(),snapshot,events);

        assertNotNull(userDataWithPointsVO);
    }




}