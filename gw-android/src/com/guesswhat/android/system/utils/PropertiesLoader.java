package com.guesswhat.android.system.utils;

import java.util.List;

import com.guesswhat.android.game.utils.QuestionsGenerator;
import com.guesswhat.android.service.cfg.ServiceFactory;
import com.guesswhat.android.service.rs.dto.QuestionDTO;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;

public class PropertiesLoader {
	
	private static final float FONT_SIZE_PERCENT = 0.02f; // 2%

	public static void loadSystemProperties(String deviceId, double density, int width) {
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper();
		
		// User id
		String value = databaseHelper.getProperty(Properties.USER_ID.toString());
		if (value == null) {
			value = System.nanoTime() + "_" + deviceId;
			databaseHelper.putProperty(Properties.USER_ID.toString(), value);
		}
		SystemProperties.USER_ID = value;
		
		// Total points
		value = databaseHelper.getProperty(Properties.TOTAL_POINTS.toString());
		if (value == null) {
			databaseHelper.putProperty(Properties.TOTAL_POINTS.toString(), "0");
			SystemProperties.TOTAL_POINTS = 0;
		} else {
			SystemProperties.TOTAL_POINTS = Integer.valueOf(value);
		}
		
		// Image type
		value = databaseHelper.getProperty(Properties.IMAGE_TYPE.toString());
		if (value == null) {
			SystemProperties.IMAGE_TYPE = detectImageType(density);
			databaseHelper.putProperty(Properties.IMAGE_TYPE.toString(), SystemProperties.IMAGE_TYPE.toString());
		} else {
			SystemProperties.IMAGE_TYPE = ImageType.valueOf(value);
		}
		
		// Font size
		value = databaseHelper.getProperty(Properties.FONT_SIZE.toString());
		if (value == null) {
			float fpixels = (float) (width * FONT_SIZE_PERCENT);
			SystemProperties.FONT_SIZE = fpixels;
			databaseHelper.putProperty(Properties.FONT_SIZE.toString(), String.valueOf(SystemProperties.FONT_SIZE));
		} else {
			SystemProperties.FONT_SIZE = Float.valueOf(value);
		}
		
		// Sound
		value = databaseHelper.getProperty(Properties.SOUND.toString());
		if (value == null) {
			SystemProperties.SOUND = true;
			databaseHelper.putProperty(Properties.SOUND.toString(), String.valueOf(SystemProperties.SOUND));
		} else {
			SystemProperties.SOUND = Boolean.valueOf(value);
		}
		
		// Database version and questions
		int version = ServiceFactory.getServiceFactory().getDatabaseService().getVersion();
		value = databaseHelper.getProperty(Properties.DATABASE_VERSION.toString());
		List<QuestionDTO> questions = null;
		if (value == null || version != Integer.valueOf(value)) {
			databaseHelper.putProperty(Properties.DATABASE_VERSION.toString(), String.valueOf(version));
			questions = ServiceFactory.getServiceFactory().getQuestionService().findQuestions();
			databaseHelper.deleteAllQuestions();
			databaseHelper.addQuestions(questions);
		} else {
			questions = databaseHelper.getAllQuestions();
		}
		if (questions == null) {
			throw new RuntimeException("Cannot load questions from server or sqlite!");
		}
		QuestionsGenerator.setQuestions(questions);
	}

	private static ImageType detectImageType(double density) {
		if (density >= 3.0) {
			return ImageType.XXHDPI;
		} else if (density >= 2.0) {
			return ImageType.XHDPI;
		} else if (density >= 1.5) {
			return ImageType.HDPI;
		} else if (density >= 1.0) {
			return ImageType.MDPI;
		} else {
			return ImageType.LDPI;
		}
	}
	
}
