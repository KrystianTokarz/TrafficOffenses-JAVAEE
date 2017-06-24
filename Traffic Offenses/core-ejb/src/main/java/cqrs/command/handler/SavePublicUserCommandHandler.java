package cqrs.command.handler;

import api.exception.command.SynchronousCommandException;
import cqrs.command.api.AsynchronousCommandHandler;
import cqrs.command.api.SynchronousCommandHandler;
import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;
import error.codes.ErrorCode;
import infrastructure.annotations.Handler;
import infrastructure.repository.api.UserRepository;
import writemodel.SavePublicUserCommand;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;

@Handler(name = "SavePublicUserCommand")
@Stateless
public class SavePublicUserCommandHandler implements SynchronousCommandHandler<SavePublicUserCommand> {

    @EJB
    private UserRepository userRepository;

    public void handle(SavePublicUserCommand command) throws SynchronousCommandException {

        DrivingLicense userDrivingLicense  = new DrivingLicense(command.getDrivingLicenseNumber()
                , command.getDrivingLicenseCreationDate(),command.getDrivingLicenseStatus());

        User user = new User.UserBuilder(command.getFirstName(),
                command.getLastName(),
                command.getEmail(),
                command.getPesel())
                .drivingLicense(userDrivingLicense)
                .build();

        userRepository.persist(user);
    }
//
//    private void persistUser(User user) throws SynchronousCommandException {
//        try {
//            userRepository.persist(user);
//        } catch(EJBTransactionRolledbackException e){
//            throw new SynchronousCommandException("Save public user persist exception", ErrorCode.SAVE_PUBLIC_USER);
//        }
//    }
}
