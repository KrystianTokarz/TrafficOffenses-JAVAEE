package readmodel;

import domainmodel.domain.user.User;

import java.io.Serializable;

public class UserDataWithPointsVO implements Serializable{

    private User user;

    private int numberOfPoints;

    public UserDataWithPointsVO(User user, int numberOfPoints) {
        this.user = user;
        this.numberOfPoints = numberOfPoints;
    }

    public User getUser() {
        return user;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }
}
