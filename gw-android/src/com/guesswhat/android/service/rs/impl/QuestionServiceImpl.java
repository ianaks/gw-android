package com.guesswhat.android.service.rs.impl;

import com.guesswhat.android.service.cfg.RestWebClient;
import com.guesswhat.android.service.rs.dto.QuestionDTO;
import com.guesswhat.android.service.rs.face.QuestionService;
import com.guesswhat.android.service.utils.ServiceUtils;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class QuestionServiceImpl implements QuestionService {

    @Override
    public List<QuestionDTO> findQuestions() {
    	String questionUrl = ServiceUtils.getQuestionUrl() + "findall";
    	
    	RestWebClient client = RestWebClient.getClient();
    	ParameterizedTypeReference<List<QuestionDTO>> responseType = new ParameterizedTypeReference<List<QuestionDTO>>() {
    	};
    	ResponseEntity<List<QuestionDTO>> result = client.exchange(questionUrl, HttpMethod.POST, null, responseType);
    	List<QuestionDTO> questions = result.getBody();
		
		return questions;
    }

}
