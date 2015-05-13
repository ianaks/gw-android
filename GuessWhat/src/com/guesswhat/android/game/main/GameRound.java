package com.guesswhat.android.game.main;

import com.guesswhat.android.persistence.model.Question;

public class GameRound {

	private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private byte [] image;
    
    public GameRound() {}

	public GameRound(Question question) {
		this.answer1 = question.getAnswer1();
		this.answer2 = question.getAnswer2();
		this.answer3 = question.getAnswer3();
		this.answer4 = question.getAnswer4();
	}

	public String getAnswer1() {
		return answer1;
	}
	
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	
	public String getAnswer2() {
		return answer2;
	}
	
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	
	public String getAnswer3() {
		return answer3;
	}
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	
	public String getAnswer4() {
		return answer4;
	}
	
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
        
}
