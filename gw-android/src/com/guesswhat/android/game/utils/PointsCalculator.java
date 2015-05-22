package com.guesswhat.android.game.utils;

import com.guesswhat.android.service.rs.dto.QuestionDTO;
import com.guesswhat.android.system.utils.SystemProperties;

public class PointsCalculator {

	public static int calculate(QuestionDTO questionDTO, String answer, int time) {
		if (questionDTO.getCorrectAnswer().equals(answer)) {
			if (time <= SystemProperties.QUESTION_TIMER) {
				return (SystemProperties.QUESTION_TIMER - time) / 1000;
			}
		}
		
		return 0;
	}
}
