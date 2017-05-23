package infrastructure.repository.implementation;

import api.exception.UserNotActiveException;
import api.exception.repository.UserNotFoundException;
import infrastructure.repository.api.UserRepository;
import infrastructure.repository.GenericRepository;
import domainmodel.domain.user.User;

import javax.ejb.Stateless;

@Stateless
public class DefaultUserRepository extends GenericRepository<User> implements UserRepository{

    public User findUserByPeselAndDrivingLicenseNumber(String pesel, String licenseNumber) throws UserNotFoundException, UserNotActiveException {
        User user = entityManager.createNamedQuery("User.findUserByPeselAndLicenseNumber", User.class)
                .setParameter("peselID", pesel)
                .setParameter("licenseNumber", licenseNumber)
                .getSingleResult();

        if(user == null) {
            throw new UserNotFoundException("User with pesel =" + pesel + ", licenseNumber = " + licenseNumber + " does not exist");
        }else if(!user.isActive()) {
            throw new UserNotActiveException("User with pesel =" + pesel + ", licenseNumber = " + licenseNumber +  " is not active");
        }
        return user;
    }

    @Override
    public User findUserByPesel(String pesel) throws UserNotFoundException, UserNotActiveException {
        User user = entityManager.createNamedQuery("User.findUserByPesel", User.class)
                .setParameter("peselID", pesel)
                .getSingleResult();

        if(user == null) {
            throw new UserNotFoundException("User with pesel =" + pesel + " does not exist");
        }else if(!user.isActive()) {
            throw new UserNotActiveException("User with pesel =" + pesel + "  is not active");
        }
        return user;
    }



}
