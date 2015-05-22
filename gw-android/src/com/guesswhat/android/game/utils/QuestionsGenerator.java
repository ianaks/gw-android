package com.guesswhat.android.game.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.guesswhat.android.service.rs.dto.QuestionDTO;
import com.guesswhat.android.system.utils.SystemProperties;

public class QuestionsGenerator {
	
	private static List<QuestionDTO> questions;
	
	private static Random random = new Random();

	public static Iterator<QuestionDTO> generate(int count) {
		Set<Integer> indexSet = new HashSet<Integer>();
		while (indexSet.size() < SystemProperties.QUESTIONS_COUNT) {
			int index = random.nextInt(questions.size());
			indexSet.add(index);
		}
		
		List<QuestionDTO> generatedQuestions = new LinkedList<QuestionDTO>();
		for (Integer index : indexSet) {
			generatedQuestions.add(questions.get(index));			
		}
		
		return generatedQuestions.iterator();
	}

	public static void setQuestions(List<QuestionDTO> questions) {
		QuestionsGenerator.questions = questions;
	}
	
}
