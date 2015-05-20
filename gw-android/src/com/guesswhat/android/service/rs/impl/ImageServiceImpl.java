package com.guesswhat.android.service.rs.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.guesswhat.android.service.rs.face.ImageService;
import com.guesswhat.android.service.utils.ServiceUtils;
import com.guesswhat.android.system.utils.ImageType;

public class ImageServiceImpl implements ImageService {

    @Override
    public byte[] findQuestionImage(String questionId, ImageType imageType) {
        return downloadImage(questionId, imageType, "question");
    }

    @Override
    public byte[] findAnswerImage(String questionId, ImageType imageType) {
    	return downloadImage(questionId, imageType, "answer");
    }
    
    private byte[] downloadImage(String questionId, ImageType type, String path) {
    	Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ServiceUtils.getImageUrl());
		Response response = null;
		
		Builder invocationBuilder = webTarget.path("find").path(path).path(questionId).path(type.toString()).request();
		invocationBuilder.header(HttpHeaders.AUTHORIZATION, ServiceUtils.getAuthorization()).accept(MediaType.APPLICATION_OCTET_STREAM);
		
		response = invocationBuilder.post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE));			
		byte[] bytes = response.readEntity(byte [].class);
		
		return bytes;
    }

}
