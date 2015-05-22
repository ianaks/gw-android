package com.guesswhat.android.service.rs.impl;

import com.guesswhat.android.service.rs.dto.RecordDTO;
import com.guesswhat.android.service.rs.face.RecordService;
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

public class RecordServiceImpl implements RecordService {

    @Override
    public void saveUserRecord(RecordDTO recordDTO) {
    	Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ServiceUtils.getRecordUrl());
		Builder invocationBuilder = webTarget.request();
		invocationBuilder.header(HttpHeaders.AUTHORIZATION, ServiceUtils.getAuthorization());				
		invocationBuilder.put(Entity.entity(recordDTO, MediaType.APPLICATION_JSON_TYPE));	
    }

    @Override
    public List<RecordDTO> findTopRecords() {
    	Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ServiceUtils.getRecordUrl());
		
		Builder invocationBuilder = webTarget.path("top").request();
		invocationBuilder.header(HttpHeaders.AUTHORIZATION, ServiceUtils.getAuthorization());
		
		Response response = invocationBuilder.accept(MediaType.APPLICATION_JSON).post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE));
		List<RecordDTO> records = response.readEntity(new GenericType<List<RecordDTO>>() {});
		
		return records;
    }

    @Override
    public Integer findUserPlace(String userId) {
    	Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(ServiceUtils.getRecordUrl());

		Builder invocationBuilder = webTarget.request();		
		invocationBuilder =  webTarget.path("place").path(userId).request();
		invocationBuilder.header(HttpHeaders.AUTHORIZATION, ServiceUtils.getAuthorization());
		
		Response response = invocationBuilder.post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE));		
		int userPlace = response.readEntity(Integer.class);
		
		return userPlace;
    }

}