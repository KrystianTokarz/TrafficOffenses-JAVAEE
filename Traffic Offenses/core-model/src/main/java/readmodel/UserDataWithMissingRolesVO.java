package readmodel;

import domainmodel.domain.user.Role;
import domainmodel.domain.user.User;
import domainmodel.domain.user.UserRole;

import java.io.Serializable;
import java.util.List;

public class UserDataWithMissingRolesVO implements Serializable {

    private User user;

    private List<String> missingUserRoles;

    public UserDataWithMissingRolesVO(User user, List<String> missingUserRoles) {
        this.user = user;
        this.missingUserRoles = missingUserRoles;
    }

    public User getUser() {
        return user;
    }

    public List<String> getMissingUserRoles() {
        return missingUserRoles;
    }
}
