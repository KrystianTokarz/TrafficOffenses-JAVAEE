package cqrs.command.handler;

import cqrs.command.api.CommandHandler;
import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;
import infrastructure.annotations.Handler;
import infrastructure.repository.api.UserRepository;
import infrastructure.repository.implementation.DefaultUserRepository;
import writemodel.SavePublicUserCommand;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;

@Handler(name = "SavePublicUserCommand")
@Stateless(mappedName = "SavePublicUserCommandHandler")
public class SavePublicUserCommandHandler implements CommandHandler<SavePublicUserCommand> {


    @EJB
    private UserRepository userRepository;


    public void handle(SavePublicUserCommand command) {


        DrivingLicense.DrivingLicenseStatus drivingLicenseStatusEnum = null;
        String drivingLicenseStatus = command.getDrivingLicenseStatus();
        if(drivingLicenseStatus.equals("ACTIVE"))
            drivingLicenseStatusEnum = DrivingLicense.DrivingLicenseStatus.ACTIVE;
        else if(drivingLicenseStatus.equals("TEMPORARY_INACTIVE"))
            drivingLicenseStatusEnum = DrivingLicense.DrivingLicenseStatus.TEMPORARY_INACTIVE;
        else{
            drivingLicenseStatusEnum = DrivingLicense.DrivingLicenseStatus.PERMAMENT_INACTIVE;
        }

        DrivingLicense userDrivingLicense = new DrivingLicense(command.getDrivingLicenseNumber(),
                command.getDrivingLicenseCreationDate(),
                drivingLicenseStatusEnum);


        User user = new User.UserBuilder(command.getFirstName(),
                command.getLastName(),
                command.getEmail(),
                command.getPesel())
                .drivingLicense(userDrivingLicense)
                .build();

        userRepository.persist(user);
    }
}
