package com.guesswhat.android.game.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.guesswhat.android.game.utils.HeartsController;
import com.guesswhat.android.game.utils.ImageDownloadProgress;
import com.guesswhat.android.game.utils.PointsCalculator;
import com.guesswhat.android.game.utils.QuestionsGenerator;
import com.guesswhat.android.service.cfg.ServiceFactory;
import com.guesswhat.android.service.rs.dto.QuestionDTO;
import com.guesswhat.android.service.rs.dto.RecordDTO;
import com.guesswhat.android.service.rs.face.ImageService;
import com.guesswhat.android.service.rs.face.RecordService;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;
import com.guesswhat.android.system.utils.Properties;
import com.guesswhat.android.system.utils.SystemProperties;

public class Game {
	
	private Iterator<QuestionDTO> questionIterator;
	private Iterator<byte []> imageIterator;
	private QuestionDTO currentQuestion;
	private GameRound currentRound;
	private int gamePoints;
	private Result result;

	private static Game instance;
	
	private Game() {}
	
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	public boolean initialize() {
		if (SystemProperties.HEARTS_COUNT == 0) {
			return false;
		}
		
		List<QuestionDTO> questions = QuestionsGenerator.generate(SystemProperties.QUESTIONS_COUNT);
		questionIterator = questions.iterator();
		imageIterator = loadImages(questions);
		currentQuestion = null;
		currentRound = null;
		gamePoints = 0;
		result = null;
		HeartsController.decreaseHearts();
		
		return true;
	}

	private Iterator<byte[]> loadImages(List<QuestionDTO> questions) {
		ImageDownloadProgress.start();
		List<byte []> images = new ArrayList<byte []>();
		ImageService imageService = ServiceFactory.getServiceFactory().getImageService();
		for (QuestionDTO questionDTO : questions) {
			byte [] image = imageService.findQuestionImage(questionDTO.getId(), SystemProperties.IMAGE_TYPE);
			images.add(image);
			ImageDownloadProgress.increment();
		}
		ImageDownloadProgress.finish();
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
	
	public boolean giveAnswer(String answer, int time) {
		int points = PointsCalculator.calculate(currentQuestion, answer, time);
		gamePoints += points;
		
		if (!hasNext()) {
			calculateResult();
		}
		
		return currentQuestion.getCorrectAnswer().equals(answer);
	}
	
	private void calculateResult() {
		SystemProperties.TOTAL_POINTS += gamePoints;
		if (gamePoints > 0) {
			DatabaseHelper helper = DatabaseHelper.getHelper();
			helper.putProperty(Properties.TOTAL_POINTS.toString(), String.valueOf(SystemProperties.TOTAL_POINTS));
			RecordService recordService = ServiceFactory.getServiceFactory().getRecordService();
			recordService.saveUserRecord(new RecordDTO(SystemProperties.USER_ID, SystemProperties.TOTAL_POINTS));
		}
		result = new Result(gamePoints, SystemProperties.TOTAL_POINTS);
	}

	public Result getResult() {
		return result;
	}
	
}
