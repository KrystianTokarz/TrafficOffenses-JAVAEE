package cqrs.command.handler;

import api.exception.command.SynchronousCommandException;
import cqrs.command.api.CommandHandler;
import cqrs.command.api.SynchronousCommandHandler;
import domainmodel.domain.user.User;
import infrastructure.annotations.Handler;
import infrastructure.repository.api.UserRepository;
import writemodel.EditUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Handler(name = "EditUserCommand")
@Stateless
public class EditUserCommandHandler implements SynchronousCommandHandler<EditUserCommand> {

    @EJB
    private UserRepository userRepository;

    @Override
    public void handle(EditUserCommand command) throws SynchronousCommandException {

        User user = userRepository.merge(command.getUser());

        if(command.getDrivingLicense() != null){
            user.setDrivingLicense(command.getDrivingLicense());
        }

    }
}
