package com.guesswhat.android.system.utils;

import java.util.List;

import com.guesswhat.android.game.utils.QuestionsGenerator;
import com.guesswhat.android.service.cfg.ServiceFactory;
import com.guesswhat.android.service.rs.dto.QuestionDTO;
import com.guesswhat.android.sqlite.helper.DatabaseHelper;

public class PropertiesLoader {

	public static void loadSystemProperties(DatabaseHelper databaseHelper, double density) {
		// Image type
		String imageType = databaseHelper.getProperty(Properties.IMAGE_TYPE.toString());
		if (imageType == null) {
			SystemProperties.IMAGE_TYPE = detectImageType(density);
			databaseHelper.putProperty(Properties.IMAGE_TYPE.toString(), SystemProperties.IMAGE_TYPE.toString());
		} else {
			SystemProperties.IMAGE_TYPE = ImageType.valueOf(imageType);
		}
		
		// Database version and questions
		int version = ServiceFactory.getServiceFactory().getDatabaseService().getVersion();
		String databaseVersion = databaseHelper.getProperty(Properties.DATABASE_VERSION.toString());
		List<QuestionDTO> questions = null;
		if (databaseVersion == null || version != Integer.valueOf(databaseVersion)) {
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
