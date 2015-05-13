package com.guesswhat.android.game.main;

import java.util.Iterator;

import com.guesswhat.android.game.utils.PointsCalculator;
import com.guesswhat.android.game.utils.QuestionsGenerator;
import com.guesswhat.android.persistence.model.Question;
import com.guesswhat.android.service.cfg.ServiceFactory;
import com.guesswhat.android.service.rs.face.ImageService;
import com.guesswhat.android.system.utils.SystemProperties;

public class Game {
	
	private Iterator<Question> questionIterator;
	private Question currentQuestion;
	private GameRound currentRound;
	private int totalPoints;
	private Result result;

	private static Game instance;
	
	private Game() {}
	
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	public void initialize() {
		questionIterator = QuestionsGenerator.generate(SystemProperties.QUESTIONS_COUNT);
		currentQuestion = null;
		currentRound = null;
		totalPoints = 0;
		result = null;
	}
	
	public GameRound next() {
		currentQuestion = questionIterator.next();
		currentRound = new GameRound(currentQuestion);
		ImageService imageService = ServiceFactory.getServiceFactory().getImageService();
		byte [] image = imageService.findQuestionImage(currentQuestion.getId(), SystemProperties.IMAGE_TYPE);
		currentRound.setImage(image);
		return currentRound;
	}
	
	public boolean hasNext() {
		return questionIterator.hasNext();
	}
	
	public int giveAnswer(String answer, int time) {
		int points = PointsCalculator.calculate(currentQuestion, answer, time);
		totalPoints += points;
		
		if (!hasNext()) {
			calculateResult();
		}
		
		return totalPoints;
	}
	
	private void calculateResult() {
		result = new Result(totalPoints, false);
	}

	public Result getResult() {
		return result;
	}
	
}
