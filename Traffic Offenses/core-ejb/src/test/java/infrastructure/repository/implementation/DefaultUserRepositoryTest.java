package infrastructure.repository.implementation;

import api.exception.repository.AggregateNotActiveException;
import api.exception.repository.AggregateNotFoundException;
import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;
import infrastructure.repository.api.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;


//@RunWith(MockitoJUnitRunner.class)
public class DefaultUserRepositoryTest {

    private  UserRepository rut;

    private  EntityManager em;

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setUpEntityManagerFactory(){
       emf =  Persistence.createEntityManagerFactory("testPU");
    }

    @Before
    public void setUp() throws Exception {

        rut = new DefaultUserRepository();
        em = emf.createEntityManager();
        Field field = DefaultUserRepository.class.getSuperclass().getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(rut, em);

    }


    @Test
    public void shouldPersistUser() throws Exception{
        User user = new User.UserBuilder("firstName", "lastName", "email", "pesel1").build();
        em.getTransaction().begin();
        rut.persist(user);
        em.flush();
        em.getTransaction().commit();

        assertThat(user.getAggregateRootEntityId()).isNotNull();
    }



    @Test
    public void shouldMergeUser() throws Exception{
        User user = new User.UserBuilder("firstName", "lastName", "email", "pesel2").build();
        em.getTransaction().begin();
        User mergeUser = rut.merge(user);
        em.getTransaction().commit();

        assertNotNull(mergeUser);
        assertThat(mergeUser.getAggregateRootEntityId()).isNotNull();
    }

    @Test
    public void shouldLoadUser() throws Exception{

        User user = new User.UserBuilder("firstName", "lastName", "email", "pesel3").build();

        em.getTransaction().begin();
        rut.persist(user);
        em.flush();
        User userInDatabase = rut.load(user.getAggregateRootEntityId());
        em.getTransaction().commit();

        assertThat(userInDatabase.getAggregateRootEntityId()).isEqualTo(user.getAggregateRootEntityId());
        assertNotNull(userInDatabase);
    }

    @Test(expected = AggregateNotFoundException.class)
    public void loadTestShouldThrowAggregateNotFoundException() throws Exception{

        em.getTransaction().begin();
        rut.load(1000L);
        em.getTransaction().commit();
    }

    @Test(expected = AggregateNotActiveException.class)
    public void loadTestShouldThrowAggregateNotActiveException() throws Exception{

        User user = new User.UserBuilder("firstName", "lastName", "email", "pesel4").build();

        em.getTransaction().begin();
        rut.persist(user);
        em.flush();
        rut.delete(user.getAggregateRootEntityId());
        em.flush();
        rut.load(user.getAggregateRootEntityId());
        em.getTransaction().commit();
    }



    @Test
    public void shouldDeleteUser() throws Exception{
        User user = new User.UserBuilder("firstName", "lastName", "email", "pesel5").build();

        em.getTransaction().begin();
        rut.persist(user);
        em.flush();
        rut.delete(user.getAggregateRootEntityId());
        em.getTransaction().commit();

        assertThat(user.isActive()).isFalse();
    }


    @Test
    public void shouldFindUserByPeselAndDrivingLicenseNumber() throws Exception{

        String pesel = "peselTest1";
        String drivingLicenseNumber = "drivingLicense1";
        User user = new User.UserBuilder("firstName","lastName","email",pesel).build();
        user.addDrivingLicense(new DrivingLicense(drivingLicenseNumber,new Date(), DrivingLicense.DrivingLicenseStatus.ACTIVE));

        em.getTransaction().begin();
        rut.persist(user);
        User userInDatabase = rut.findUserByPeselAndDrivingLicenseNumber(pesel,drivingLicenseNumber);
        em.getTransaction().commit();

        assertNotNull(userInDatabase);
        assertThat(userInDatabase.getPesel()).isEqualTo(pesel);
        assertThat(userInDatabase.getDrivingLicense().getLicenseNumber()).isEqualTo(drivingLicenseNumber);
    }


    @Test(expected = UserNotFoundException.class)
    public void findUserByPeselAndDrivingLicenseNumberTestShouldThrowUserNotFoundException() throws Exception{

        String pesel = "peselTestNotFound";
        String drivingLicenseNumber = "drivingLicenseNotFound";

        em.getTransaction().begin();
        rut.findUserByPeselAndDrivingLicenseNumber(pesel,drivingLicenseNumber);
        em.getTransaction().commit();
    }


    @Test(expected = UserNotActiveException.class)
    public void findUserByPeselAndDrivingLicenseNumberTestShouldThrowUserNotActiveException() throws Exception{

        String pesel = "peselTest2";
        String drivingLicenseNumber = "drivingLicense2";
        User user = new User.UserBuilder("firstName","lastName","email",pesel).build();
        user.addDrivingLicense(new DrivingLicense(drivingLicenseNumber,new Date(), DrivingLicense.DrivingLicenseStatus.ACTIVE));

        em.getTransaction().begin();
        rut.persist(user);
        rut.delete(user.getAggregateRootEntityId());
        em.flush();
        rut.findUserByPeselAndDrivingLicenseNumber(pesel,drivingLicenseNumber);
        em.getTransaction().commit();
    }


    @Test
    public void shouldFindUserByPesel() throws Exception{

        String pesel = "peselTest3";
        String drivingLicenseNumber = "drivingLicense1";
        User user = new User.UserBuilder("firstName","lastName","email",pesel).build();

        em.getTransaction().begin();
        rut.persist(user);
        User userInDatabase = rut.findUserByPesel(pesel);
        em.getTransaction().commit();

        assertNotNull(userInDatabase);
        assertThat(userInDatabase.getPesel()).isEqualTo(pesel);
    }


    @Test(expected = UserNotFoundException.class)
    public void findUserByPeselTestShouldThrowUserNotFoundException() throws Exception{

        String pesel = "peselTestNotFound3";

        em.getTransaction().begin();
        rut.findUserByPesel(pesel);
        em.getTransaction().commit();
    }


    @Test(expected = UserNotActiveException.class)
    public void findUserByPeselTestShouldThrowUserNotActiveException() throws Exception{

        String pesel = "peselTest4";
        User user = new User.UserBuilder("firstName","lastName","email",pesel).build();


        em.getTransaction().begin();
        rut.persist(user);
        rut.delete(user.getAggregateRootEntityId());
        em.flush();
        rut.findUserByPesel(pesel);
        em.getTransaction().commit();
    }



//    @Test

//    @Test
//    public void findUserByPesel() throws Exception {
//
//    }

}