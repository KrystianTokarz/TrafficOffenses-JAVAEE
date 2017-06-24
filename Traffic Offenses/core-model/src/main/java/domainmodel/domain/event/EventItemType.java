package domainmodel.domain.event;

public enum EventItemType {
    SPEED_LIMIT_10(2,50,100),
    SPEED_LIMIT_20(4,100,200),
    SPEED_LIMIT_30(6,200,300),
    SPEED_LIMIT_40(8,300,400),
    SPEED_LIMIT_50(10,400,500),
    DRIVE_RED_LIGHT(8,250,300),
    CAR_ACCIDENT(6,300,500),
    CAR_BUMP(4,200,400),
    NO_SEAT_BELTS(5,100,200),
    TURN_OFF_LIGHT(3,100,200),
    NOT_ALLOWED_TURN(6,200,300),
    NOT_ALLOWED_OVERTAKING(6,300,400);

    private int points;
    private int minAmount;
    private int maxAmount;

    EventItemType(int points,int minAmount, int maxAmount){
        this.points = points;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public int getPoints() {
        return points;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}
