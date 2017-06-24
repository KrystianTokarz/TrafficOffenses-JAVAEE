package cqrs.command.handler;

import api.exception.command.SynchronousCommandException;
import api.exception.repository.AggregateNotActiveException;
import api.exception.repository.AggregateNotFoundException;
import cqrs.command.api.SynchronousCommandHandler;
import domainmodel.domain.user.User;
import error.codes.ErrorCode;
import infrastructure.annotations.Handler;
import infrastructure.repository.api.UserRepository;
import org.slf4j.Logger;
import writemodel.ActiveUserCommand;
import writemodel.AddNewRoleForUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Handler(name = "ActiveUserCommand")
@Stateless
public class ActiveUserCommandHandler  implements SynchronousCommandHandler<ActiveUserCommand> {

    @EJB
    private UserRepository userRepository;

    @Inject
    private Logger logger;


    @Override
    public void handle(ActiveUserCommand command) throws SynchronousCommandException {
        try {
            User user = userRepository.load(command.getId());
            user.setAggregateAsActive();
        } catch (AggregateNotFoundException e) {
            logger.warn("ActiveUserCommandHandler - User with id = {0} is not found", command.getId());
            throw new SynchronousCommandException("ActiveUserCommandHandler handle exception", ErrorCode.USER_NOT_FOUND);
        }
    }
}
