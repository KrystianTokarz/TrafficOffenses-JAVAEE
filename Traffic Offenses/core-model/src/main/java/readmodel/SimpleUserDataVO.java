package readmodel;

import domainmodel.domain.user.User;

import java.io.Serializable;

public class SimpleUserDataVO implements Serializable{

    private User user;

    public SimpleUserDataVO(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
