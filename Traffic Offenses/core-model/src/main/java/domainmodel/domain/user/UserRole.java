package domainmodel.domain.user;

import domainmodel.suport.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLE")
public class UserRole extends BaseEntity {


    @Enumerated(EnumType.STRING)
    private Role role;

    public UserRole() {
    }

    public UserRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

}
