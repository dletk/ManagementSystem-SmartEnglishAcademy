package Model;

public class Shift {
    private User user;
    private String startingTime, endingTime;
    private float length;

    public Shift(User user, String startingTime, String endingTime, float length) {
        this.user = user;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.length = length;
    }

    public User getUser() {
        return user;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public float getLength() {
        return length;
    }
}
