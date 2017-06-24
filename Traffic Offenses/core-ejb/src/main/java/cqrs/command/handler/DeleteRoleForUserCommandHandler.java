package cqrs.command.handler;

import api.exception.command.SynchronousCommandException;
import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import cqrs.command.api.SynchronousCommandHandler;
import domainmodel.domain.user.Role;
import domainmodel.domain.user.User;
import domainmodel.domain.user.UserRole;
import error.codes.ErrorCode;
import infrastructure.annotations.Handler;
import infrastructure.creation.RoleFactory;
import infrastructure.repository.api.UserRepository;
import org.slf4j.Logger;
import writemodel.AddNewRoleForUserCommand;
import writemodel.DeleteRoleForUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Handler(name = "DeleteRoleForUserCommand")
@Stateless
public class DeleteRoleForUserCommandHandler implements SynchronousCommandHandler<DeleteRoleForUserCommand> {

    @EJB
    private UserRepository userRepository;

    @Inject
    private Logger logger;

    public void handle(DeleteRoleForUserCommand command) throws SynchronousCommandException {

        try {
            User user = userRepository.findUserByPesel(command.getPesel());
            deleteUserRole(user, command.getRoleName());
        } catch (UserNotFoundException e) {
            logger.warn("DeleteRoleForUserCommandHandler - User with pesel = {0} is not found", command.getPesel());
            throw new SynchronousCommandException("DeleteRoleForUserCommandHandler handle exception", ErrorCode.USER_NOT_FOUND);
        } catch (UserNotActiveException e) {
            logger.warn("DeleteRoleForUserCommandHandler - User with pesel = {0} is not active", command.getPesel());
            throw new SynchronousCommandException("DeleteRoleForUserCommandHandler handle exception", ErrorCode.USER_NOT_ACTIVE);
        }
    }

    private void deleteUserRole(User user,String roleToDelete){
        Role role = RoleFactory.createRole(roleToDelete);
        UserRole userRole = user.deleteUserRole(role);
        userRepository.deleteUserRole(userRole);
    }


}
