package com.guesswhat.android.service.rs.impl;

import com.guesswhat.android.service.cfg.RestWebClient;
import com.guesswhat.android.service.rs.face.DatabaseService;
import com.guesswhat.android.service.utils.ServiceUtils;

public class DatabaseServiceImpl implements DatabaseService {

    @Override
    public Integer getVersion() {
    	String databaseUrl = ServiceUtils.getDatabaseUrl() + "version";
    	
    	RestWebClient client = RestWebClient.getClient();
    	int version = client.postForObject(databaseUrl, null, Integer.class);
		
		return version;
    }

}
