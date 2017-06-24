package cqrs.command.handler;

import api.exception.command.SynchronousCommandException;
import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import cqrs.command.api.AsynchronousCommandHandler;
import cqrs.command.api.SynchronousCommandHandler;
import domainmodel.domain.user.Role;
import domainmodel.domain.user.User;
import error.codes.ErrorCode;
import infrastructure.annotations.Handler;

import infrastructure.creation.RoleFactory;
import infrastructure.repository.api.UserRepository;
import org.slf4j.Logger;
import writemodel.AddNewRoleForUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Handler(name = "AddNewRoleForUserCommand")
@Stateless
public class AddNewRoleForUserCommandHandler implements SynchronousCommandHandler<AddNewRoleForUserCommand> {

    @EJB
    private UserRepository userRepository;

    @Inject
    private Logger logger;


    public void handle(AddNewRoleForUserCommand command)  throws SynchronousCommandException {

        try {
            String pesel = command.getPesel();
            User user = userRepository.findUserByPesel(pesel);
            getNewRoleForUser(user, command.getRoleName());
        } catch (UserNotFoundException e) {
            logger.warn("AddNewRoleForUserCommandHandler - User with pesel = {0} is not found", command.getPesel());
            throw new SynchronousCommandException("AddNewRoleForUserCommandHandler handle exception", ErrorCode.USER_NOT_FOUND);
        } catch (UserNotActiveException e) {
            logger.warn("AddNewRoleForUserCommandHandler - User with pesel = {0} is not active", command.getPesel());
            throw new SynchronousCommandException("AddNewRoleForUserCommandHandler handle exception", ErrorCode.USER_NOT_ACTIVE);
        }
    }

    public void getNewRoleForUser(User user, String newRole){
        Role role =  RoleFactory.createRole(newRole);
        user.addUserRole(role);
    }
}
