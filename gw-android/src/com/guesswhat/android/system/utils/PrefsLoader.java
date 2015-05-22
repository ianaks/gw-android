package com.guesswhat.android.system.utils;

import java.util.List;

import com.guesswhat.android.game.utils.QuestionsGenerator;
import com.guesswhat.android.service.cfg.ServiceFactory;
import com.guesswhat.android.service.rs.dto.QuestionDTO;

public class PrefsLoader {

	public static void load(double density) {
		loadSystemProperties(density);
    	checkDatabaseVersion();
	}

	private static void loadSystemProperties(double density) {
		if (density >= 3.0) {
			SystemProperties.IMAGE_TYPE = ImageType.XXHDPI;
		} else if (density >= 2.0) {
			SystemProperties.IMAGE_TYPE = ImageType.XHDPI;
		} else if (density >= 1.5) {
			SystemProperties.IMAGE_TYPE = ImageType.HDPI;
		} else if (density >= 1.0) {
			SystemProperties.IMAGE_TYPE = ImageType.MDPI;
		} else {
			SystemProperties.IMAGE_TYPE = ImageType.LDPI;
		}
	}
	
	private static void checkDatabaseVersion() {
		int version = ServiceFactory.getServiceFactory().getDatabaseService().getVersion();
		if (version != SystemProperties.DATABASE_VERSION) {
			List<QuestionDTO> questions = ServiceFactory.getServiceFactory().getQuestionService().findQuestions();
			QuestionsGenerator.setQuestions(questions);
		}
	}
	
}
