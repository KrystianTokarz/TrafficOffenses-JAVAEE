package infrastructure.repository.implementation;

import api.exception.repository.user.DrivingLicenseNotFoundException;
import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import domainmodel.domain.user.UserRole;
import domainmodel.embaddable.DrivingLicense;
import infrastructure.repository.api.UserRepository;
import infrastructure.repository.GenericRepository;
import domainmodel.domain.user.User;

import javax.ejb.Stateless;
import java.util.List;

@Stateless(name = "defaultUserRepository")
public class DefaultUserRepository extends GenericRepository<User> implements UserRepository{

    public User findUserByPeselAndDrivingLicenseNumber(String pesel, String licenseNumber) throws UserNotFoundException,
                                                                                                  UserNotActiveException {

        List<User> users = entityManager.createNamedQuery("User.findUserByPeselAndLicenseNumber", User.class)
                .setParameter("peselID", pesel)
                .setParameter("licenseNumber", licenseNumber)
                .getResultList();

        if(users.isEmpty()) {
            throw new UserNotFoundException("User with pesel =" + pesel + ", licenseNumber = " + licenseNumber + " does not exist");
        }else if(!users.get(0).isActive()) {
            throw new UserNotActiveException("User with pesel =" + pesel + ", licenseNumber = " + licenseNumber +  " is not active");
        }
        return users.get(0);
    }

    @Override
    public User findUserByPesel(String pesel) throws UserNotFoundException, UserNotActiveException {

        List<User> users = entityManager.createNamedQuery("User.findUserByPesel", User.class)
                                            .setParameter("peselID", pesel)
                                            .getResultList();

        if(users.isEmpty()) {
            throw new UserNotFoundException("User with pesel =" + pesel + " does not exist");
        }else if(!users.get(0).isActive()) {
            throw new UserNotActiveException("User with pesel =" + pesel + "  is not active");
        }
        return users.get(0);
    }

    @Override
    public User findUserByDrivingLicenseNumber(String drivingLicenseNumber) throws UserNotFoundException, UserNotActiveException {
        List<User> users = entityManager.createNamedQuery("User.findUserByLicenseNumber", User.class)
                .setParameter("licenseNumber", drivingLicenseNumber)
                .getResultList();

        if(users.isEmpty()) {
            throw new UserNotFoundException("User with drivingLicense =" + drivingLicenseNumber + " does not exist");
        }else if(!users.get(0).isActive()) {
            throw new UserNotActiveException("User with drivingLicense =" + drivingLicenseNumber + "  is not active");
        }
        return users.get(0);
    }


    @Override
    public List<User> findAllActiveDrivers(){
        List<User> users = entityManager.createNamedQuery("User.findAllActiveDrivers", User.class)
                .getResultList();

        return users;
    }

    @Override
    public void deleteUserRole(UserRole userRole){
        entityManager.remove(userRole);
    }

    @Override
    public List<User> findAllUser(){
        List<User> users = entityManager.createNamedQuery("User.findAllUser", User.class)
                .getResultList();

        return users;
    }

}
