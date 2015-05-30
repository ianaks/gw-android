package com.guesswhat.android.service.rs.impl;

import com.guesswhat.android.service.cfg.RestWebClient;
import com.guesswhat.android.service.rs.dto.RecordDTO;
import com.guesswhat.android.service.rs.face.RecordService;
import com.guesswhat.android.service.utils.ServiceUtils;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class RecordServiceImpl implements RecordService {

    @Override
    public void saveUserRecord(RecordDTO recordDTO) {
    	String recordUrl = ServiceUtils.getRecordUrl();
    	
    	RestWebClient client = RestWebClient.getClient();
    	client.put(recordUrl, recordDTO);
    }

    @Override
    public List<RecordDTO> findTopRecords() {
    	String recordUrl = ServiceUtils.getRecordUrl() + "/top";
    	
    	RestWebClient client = RestWebClient.getClient();
    	ParameterizedTypeReference<List<RecordDTO>> responseType = new ParameterizedTypeReference<List<RecordDTO>>() {
    	};
    	ResponseEntity<List<RecordDTO>> result = client.exchange(recordUrl, HttpMethod.POST, null, responseType);
    	List<RecordDTO> records = result.getBody();
		
		return records;
    }

    @Override
    public Integer findUserPlace(String userId) {
    	String recordUrl = ServiceUtils.getRecordUrl() + "/place/" + userId;
    	
    	RestWebClient client = RestWebClient.getClient();
    	int userPlace = client.postForObject(recordUrl, null, Integer.class);
		
		return userPlace;
    }

}