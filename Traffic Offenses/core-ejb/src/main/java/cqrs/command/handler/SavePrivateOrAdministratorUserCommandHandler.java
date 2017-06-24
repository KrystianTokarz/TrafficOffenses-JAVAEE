package cqrs.command.handler;

import api.exception.command.SynchronousCommandException;
import cqrs.command.api.SynchronousCommandHandler;
import domainmodel.domain.user.Role;
import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;
import error.codes.ErrorCode;
import infrastructure.annotations.Handler;

import infrastructure.repository.api.UserRepository;
import writemodel.SavePrivateOrAdministratorUserCommand;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

@Handler(name = "SavePrivateOrAdministratorUserCommand")
@Stateless
public class SavePrivateOrAdministratorUserCommandHandler implements SynchronousCommandHandler<SavePrivateOrAdministratorUserCommand> {

    @EJB
    private UserRepository userRepository;

    public void handle(SavePrivateOrAdministratorUserCommand command) throws SynchronousCommandException {

        DrivingLicense userDrivingLicense = null ;

        if(command.isHasDrivingLicenseNumber()){
            userDrivingLicense = new DrivingLicense(command.getDrivingLicenseNumber()
                    , command.getDrivingLicenseCreationDate(),command.getDrivingLicenseStatus());
        }

        User user = createUser(command.getRole(),command.getFirstName(),command.getLastName(),command.getEmail(),
                command.getPesel(),command.getPassword(),userDrivingLicense);

        userRepository.persist(user);
    }

    private User createUser(Role role, String firstName, String lastName, String email, String pesel, String password, DrivingLicense drivingLicense){
        User user = null;

        if(role.equals(Role.ROLE_ADMINISTRATOR))
            user = createAdministratorUser(firstName,lastName,email,pesel,password,drivingLicense);
        else if(role.equals(Role.ROLE_PRIVATE))
            user = createPrivateUser(firstName,lastName,email,pesel,password,drivingLicense);
        return user;

    }

    private User createPrivateUser(String firstName, String lastName, String email, String pesel, String password, DrivingLicense drivingLicense){
        return new User.UserBuilder(firstName,lastName,email,pesel)
                .privateRole(password)
                .drivingLicense(drivingLicense)
                .build();
    }

    private User createAdministratorUser(String firstName, String lastName, String email, String pesel, String password, DrivingLicense drivingLicense){
        return new User.UserBuilder(firstName,lastName,email,pesel)
                .administratorRole(password)
                .drivingLicense(drivingLicense)
                .build();
    }
}
