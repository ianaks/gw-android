package com.guesswhat.android.sqlite.model;

public class Record {

    private int userId;
    private int points;

    public Record() {

    }

    public Record(int userId, int points) {
        super();
        this.userId = userId;
        this.points = points;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
