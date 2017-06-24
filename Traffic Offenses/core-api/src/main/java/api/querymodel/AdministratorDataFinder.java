package api.querymodel;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import domainmodel.domain.user.User;
import readmodel.SimpleUserDataVO;
import readmodel.UserDataWithMissingRolesVO;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface AdministratorDataFinder {
    SimpleUserDataVO findUserWithTheirRolesByPesel(String pesel) throws UserNotFoundException, UserNotActiveException;
    List<User> findAllUser();
}
