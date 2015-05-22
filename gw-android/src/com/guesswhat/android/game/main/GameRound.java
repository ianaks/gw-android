package com.guesswhat.android.game.main;

import com.guesswhat.android.service.rs.dto.QuestionDTO;

public class GameRound {

	private String [] answers;
    private byte [] image;
    
    public GameRound() {}

	public GameRound(QuestionDTO questionDTO) {
		answers = new String[4];
		this.answers[0] = questionDTO.getAnswer1();
		this.answers[1] = questionDTO.getAnswer2();
		this.answers[2] = questionDTO.getAnswer3();
		this.answers[3] = questionDTO.getAnswer4();
	}
	
	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
        
}
