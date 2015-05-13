package com.guesswhat.android.game.main;

public class Result {

	private int points;
	private boolean isNewRecord;
	
	public Result() {}
	
	public Result(int points, boolean isNewRecord) {
		super();
		this.points = points;
		this.isNewRecord = isNewRecord;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public boolean isNewRecord() {
		return isNewRecord;
	}
	
	public void setNewRecord(boolean isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	
	
}
