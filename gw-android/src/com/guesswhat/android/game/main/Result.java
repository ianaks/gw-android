package com.guesswhat.android.game.main;

public class Result {

	private int gamePoints;
	private int totalPoints;
	
	public Result() {}

	public Result(int gamePoints, int totalPoints) {
		super();
		this.gamePoints = gamePoints;
		this.totalPoints = totalPoints;
	}

	public int getGamePoints() {
		return gamePoints;
	}

	public void setGamePoints(int gamePoints) {
		this.gamePoints = gamePoints;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	
}
