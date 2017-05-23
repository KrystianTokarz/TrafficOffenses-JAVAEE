package domainmodel.domain.user;

import domainmodel.suport.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLE")
public class UserRole extends BaseEntity {


    private String role;

    public UserRole() {
    }

    public UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
