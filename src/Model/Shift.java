package Model;

public class Shift {
    private User user;
    private String startingTime, endingTime, date;
    private Float length;

    public Shift(User user, String startingTime, String endingTime, float length) {
        this.user = user;

        this.startingTime = startingTime.split(" ")[1];
        this.date = startingTime.split(" ")[0];

        //        Because these value are optional in database, a shift can be missing some of them.
        if (endingTime != null) {
            this.endingTime = endingTime.split(" ")[1];
        } else {
            this.endingTime = "";
        }
        if (endingTime != null) {
            this.length = length;
        } else {
            this.length = 0f;
        }
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

    public String getDate() {
        return date;
    }
}
