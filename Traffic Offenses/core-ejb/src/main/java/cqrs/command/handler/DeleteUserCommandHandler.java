package cqrs.command.handler;

import api.exception.command.SynchronousCommandException;
import api.exception.repository.AggregateNotActiveException;
import api.exception.repository.AggregateNotFoundException;
import cqrs.command.api.SynchronousCommandHandler;
import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;
import error.codes.ErrorCode;
import infrastructure.annotations.Handler;
import infrastructure.repository.api.UserRepository;
import org.slf4j.Logger;
import writemodel.DeleteUserCommand;
import writemodel.SavePublicUserCommand;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Handler(name = "DeleteUserCommand")
@Stateless
public class DeleteUserCommandHandler implements SynchronousCommandHandler<DeleteUserCommand> {


    @EJB
    private UserRepository userRepository;

    @Inject
    private Logger logger;


    public void handle(DeleteUserCommand command) throws SynchronousCommandException {
        try {
            User user = userRepository.load(command.getId());
            user.setAggregateAsRemoved();;
        } catch (AggregateNotFoundException e) {
            logger.warn("DeleteUserCommandHandler - User with id = {0} is not found", command.getId());
            throw new SynchronousCommandException("DeleteUserCommandHandler handle exception", ErrorCode.USER_NOT_FOUND);
        }


    }





}
