package infrastructure.creation;

import domainmodel.domain.user.Role;

public class RoleFactory {

    private static final String PRIVATE_ROLE = "ROLE_PRIVATE";
    private static final String PUBLIC_ROLE = "ROLE_PUBLIC";
    private static final String ADMINISTRATOR_ROLE = "ROLE_ADMINISTRATOR";


    public static Role createRole(String role){
        Role userRole = null;
        if(role.equals(PRIVATE_ROLE))
            userRole =  Role.ROLE_PRIVATE;
        else if(role.equals(PUBLIC_ROLE))
            userRole =  Role.ROLE_PUBLIC;
        else if(role.equals(ADMINISTRATOR_ROLE))
            userRole =  Role.ROLE_ADMINISTRATOR;

        return userRole;
    }
}
