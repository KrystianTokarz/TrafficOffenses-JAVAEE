package infrastructure.repository.implementation;

import domainmodel.domain.event.Event;
import domainmodel.embaddable.UserData;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.api.SnapshotRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class DefaultEventRepositoryTest {

    private EventRepository rut;

    private EntityManager em;

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setUpEntityManagerFactory(){
        emf =  Persistence.createEntityManagerFactory("testPU");
    }

    @Before
    public void setUp() throws Exception {

        rut = new DefaultEventRepository();
        em = emf.createEntityManager();
        Field field = DefaultUserRepository.class.getSuperclass().getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(rut, em);

    }

    @Test
    public void shouldFindEventsWithVersion() throws Exception {

        Long userId = 50l;
        Long eventVersion1 = 5l;
        Long eventVersion2 = 10l;
        Event event1 = new Event(new UserData(userId, "","","","",""),null,eventVersion1);
        Event event2 = new Event(new UserData(userId, "","","","",""),null,eventVersion1);
        Event event3 = new Event(new UserData(userId, "","","","",""),null,eventVersion1);
        Event event4 = new Event(new UserData(userId, "","","","",""),null,eventVersion2);
        Event event5 = new Event(new UserData(userId, "","","","",""),null,eventVersion2);

        em.getTransaction().begin();
        rut.persist(event1);
        rut.persist(event2);
        rut.persist(event3);
        rut.persist(event4);
        rut.persist(event5);
        em.flush();

        List<Event> eventsInDBWithSelectVersion = rut.findEventsWithVersion(userId,eventVersion1);

        em.getTransaction().commit();

        assertThat(eventsInDBWithSelectVersion.get(0).getEventVersion()).isEqualTo(eventVersion1);
        assertThat(eventsInDBWithSelectVersion).hasSize(3);


    }



}