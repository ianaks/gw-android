package com.guesswhat.android.service.rs.impl;

import com.guesswhat.android.service.rs.dto.QuestionDTO;
import com.guesswhat.android.service.rs.face.QuestionService;
import com.guesswhat.android.service.utils.ServiceUtils;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class QuestionServiceImpl implements QuestionService {

    @Override
    public List<QuestionDTO> findQuestions() {
    	Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ServiceUtils.getQuestionUrl());
		Response response = null;
		
		Builder invocationBuilder = webTarget.path("findall").request();
		invocationBuilder.header(HttpHeaders.AUTHORIZATION, ServiceUtils.getAuthorization());
		
		response = invocationBuilder.post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE));		
		List<QuestionDTO> questions = response.readEntity(new GenericType<List<QuestionDTO>>() {});
		
		return questions;
    }

}
