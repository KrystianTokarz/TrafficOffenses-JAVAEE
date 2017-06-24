package infrastructure.repository.implementation;

import api.exception.repository.snapshot.SnapshotNotActiveException;
import api.exception.repository.snapshot.SnapshotNotFoundException;
import api.exception.repository.snapshot.SnapshotWithVersionNotActiveException;
import api.exception.repository.snapshot.SnapshotWithVersionNotFoundException;
import domainmodel.domain.snapshot.Snapshot;
import domainmodel.domain.snapshot.SnapshotEntity;
import domainmodel.domain.user.User;
import domainmodel.embaddable.UserData;
import infrastructure.repository.api.EventRepository;
import infrastructure.repository.api.SnapshotRepository;
import infrastructure.repository.api.UserRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Field;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class DefaultSnapshotRepositoryTest {

    private SnapshotRepository rut;

    private EntityManager em;

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setUpEntityManagerFactory(){
        emf =  Persistence.createEntityManagerFactory("testPU");
    }

    @Before
    public void setUp() throws Exception {

        rut = new DefaultSnapshotRepository();
        em = emf.createEntityManager();
        Field field = DefaultUserRepository.class.getSuperclass().getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(rut, em);

    }

    @Test
    public void shouldFindLastSnapshot() throws Exception {

        Long userId = 1l;
        SnapshotEntity firstSnapshot = new SnapshotEntity(0, 0l, new UserData(userId,"","","","",""));
        SnapshotEntity secondSnapshot = new SnapshotEntity(0, 1l, new UserData(userId,"","","","",""));

        Field creationDate = SnapshotEntity.class.getDeclaredField("creationDate");
        creationDate.setAccessible(true);
        creationDate.set(firstSnapshot, new Date(10));

        em.getTransaction().begin();
        rut.persist(firstSnapshot);
        rut.persist(secondSnapshot);
        em.flush();
        Snapshot snapshotInDatabasae = rut.findLastSnapshot(userId);
        em.getTransaction().commit();

        assertNotNull(snapshotInDatabasae);
        assertThat(snapshotInDatabasae.getEventVersion()).isEqualTo(secondSnapshot.getEventVersion());
    }

    @Test(expected = SnapshotNotFoundException.class)
    public void testFindLastSnapshotThrowSnapshotNotFoundException() throws Exception{
        em.getTransaction().begin();
        rut.findLastSnapshot(555l);
        em.getTransaction().commit();

    }



    @Test
    public void shouldFindSnapshotWithEventVersion() throws Exception {

        Long userId = 1l;
        SnapshotEntity snapshot1 = new SnapshotEntity(0, 10l, new UserData(userId,"","","","",""));
        SnapshotEntity snapshot2 = new SnapshotEntity(0, 11l, new UserData(userId,"","","","",""));
        SnapshotEntity snapshot3 = new SnapshotEntity(0, 12l, new UserData(userId,"","","","",""));


        em.getTransaction().begin();
        rut.persist(snapshot1);
        rut.persist(snapshot2);
        rut.persist(snapshot3);
        em.flush();
        Snapshot snapshotInDatabase = rut.findSnapshotWithEventVersion(userId,12l);
        em.getTransaction().commit();

        assertNotNull(snapshotInDatabase);
        assertThat(snapshotInDatabase.getEventVersion()).isEqualTo(snapshot3.getEventVersion());
    }

    @Test(expected = SnapshotWithVersionNotFoundException.class)
    public void testFindSnapshotWithEventVersionThrowSnapshotWithVersionNotFoundException() throws Exception{

        em.getTransaction().begin();
        rut.findSnapshotWithEventVersion(1l,1000l);
        em.getTransaction().commit();

    }

    @Test(expected = SnapshotWithVersionNotActiveException.class)
    public void estFindSnapshotWithEventVersionThrowSnapshotWithVersionNotActiveException() throws Exception{
        Long userId = 1l;
        Long version = 10l;
        SnapshotEntity secondSnapshot = new SnapshotEntity(0, version, new UserData(userId,"","","","",""));
        em.getTransaction().begin();
        em.persist(secondSnapshot);
        rut.delete(secondSnapshot.getAggregateRootEntityId());
        em.flush();
        rut.findSnapshotWithEventVersion(userId,version);
        em.getTransaction().commit();

    }



}