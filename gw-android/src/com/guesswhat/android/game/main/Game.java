package com.guesswhat.android.game.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.guesswhat.android.game.utils.PointsCalculator;
import com.guesswhat.android.game.utils.QuestionsGenerator;
import com.guesswhat.android.service.cfg.ServiceFactory;
import com.guesswhat.android.service.rs.dto.QuestionDTO;
import com.guesswhat.android.service.rs.dto.RecordDTO;
import com.guesswhat.android.service.rs.face.ImageService;
import com.guesswhat.android.system.utils.SystemProperties;

public class Game {
	
	private Iterator<QuestionDTO> questionIterator;
	private Iterator<byte []> imageIterator;
	private QuestionDTO currentQuestion;
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
		List<QuestionDTO> questions = QuestionsGenerator.generate(SystemProperties.QUESTIONS_COUNT);
		questionIterator = questions.iterator();
		imageIterator = loadImages(questions);
		currentQuestion = null;
		currentRound = null;
		totalPoints = 0;
		result = null;
	}
	
	private Iterator<byte[]> loadImages(List<QuestionDTO> questions) {
		List<byte []> images = new ArrayList<byte []>();
		ImageService imageService = ServiceFactory.getServiceFactory().getImageService();
		for (QuestionDTO questionDTO : questions) {
			byte [] image = imageService.findQuestionImage(questionDTO.getId(), SystemProperties.IMAGE_TYPE);
			images.add(image);
		}
		return images.iterator();
	}

	public GameRound next() {
		if (hasNext()) {
			currentQuestion = questionIterator.next();
			currentRound = new GameRound(currentQuestion);
			currentRound.setImage(imageIterator.next());
			return currentRound;
		} else {
			throw new RuntimeException("There is no next element!");
		}
	}
	
	public boolean hasNext() {
		return questionIterator.hasNext() && imageIterator.hasNext();
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
		boolean isNewRecord = false;
		if (totalPoints > 10) { // replace 10 with real user local record
			isNewRecord = true;
			// save new record to DB
			ServiceFactory.getServiceFactory().getRecordService().saveUserRecord(new RecordDTO("userId", totalPoints)); // replace "userId" with real one
		}
		result = new Result(totalPoints, isNewRecord);
	}

	public Result getResult() {
		return result;
	}
	
}
