//package cqrs.query.finder.helper;
//
//import api.exception.repository.event.EventsWithVersionNotFoundException;
//import domainmodel.domain.event.Event;
//import domainmodel.domain.snapshot.Snapshot;
//import domainmodel.domain.snapshot.SnapshotEntity;
//import infrastructure.repository.implementation.DefaultEventRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.*;
//import static org.mockito.BDDMockito.given;
//
//@RunWith(MockitoJUnitRunner.class)
//public class EventHelperFinderTest {
//
//    @Mock
//    private DefaultEventRepository eventRepositoryMock;
//
//    @InjectMocks
//    private EventHelperFinderBean sut;
//
//
//    @Test
//    public void shouldFindUserEvents() throws Exception {
//
//        Long userId = 1l;
//        Long snapshotVersion = 0l;
//        Snapshot snapshot = new SnapshotEntity(0, snapshotVersion, null);
//        List<Event> events = new ArrayList<>();
//
//        events.add(new Event());
//
//        //given
//        given(eventRepositoryMock.findEventsWithVersion(userId,snapshotVersion)).willReturn(events);
//        //when
//        List<Event> userEvents = sut.findUserEvents(userId,snapshot.getEventVersion());
//        //then
//        assertNotNull(userEvents);
//        assertThat(userEvents.isEmpty()).isFalse();
//
//    }
//
//    @Test
//    public void shouldNotFindUserEvents() throws Exception {
//
//        Long userId = 1l;
//        Long snapshotVersion = 0l;
//        Snapshot snapshot = new SnapshotEntity(0, snapshotVersion, null);
//
//        //given
//        given(eventRepositoryMock.findEventsWithVersion(userId,snapshotVersion)).willThrow(EventsWithVersionNotFoundException.class);
//        //when
//        List<Event> userEvents = sut.findUserEvents(userId,snapshot.getEventVersion());
//        //then
//        assertNotNull(userEvents);
//        assertThat(userEvents.isEmpty()).isTrue();
//
//    }
//
//}