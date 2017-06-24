package cqrs.query.finder;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.AdministratorDataFinder;
import cqrs.query.api.VisualObjectDataTransfer;
import domainmodel.domain.user.User;
import domainmodel.domain.user.UserRole;
import infrastructure.repository.api.UserRepository;
import readmodel.SimpleUserDataVO;
import readmodel.UserDataWithMissingRolesVO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Collection;
import java.util.List;

@Stateless
public class AdministratorDataFinderBean implements AdministratorDataFinder {

    @EJB
    private UserRepository userRepository;

    @EJB
    private VisualObjectDataTransfer visualObjectDataTransfer;

    @Override
    public List<User> findAllUser(){
       return userRepository.findAllUser();
    }

    @Override
    public SimpleUserDataVO findUserWithTheirRolesByPesel(String pesel) throws UserNotFoundException, UserNotActiveException {
        User user = userRepository.findUserByPesel(pesel);
        return visualObjectDataTransfer.transferUserToVO(user);
    }


}
