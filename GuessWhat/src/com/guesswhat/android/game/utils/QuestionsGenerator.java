package com.guesswhat.android.game.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.guesswhat.android.persistence.model.Question;

public class QuestionsGenerator {

	public static Iterator<Question> generate(int count) {
		List<Question> questions = new LinkedList<Question>();
		for (int i = 0; i < count; ++i) {
			questions.add(new Question());
		}
		return questions.iterator();
	}
	
}
