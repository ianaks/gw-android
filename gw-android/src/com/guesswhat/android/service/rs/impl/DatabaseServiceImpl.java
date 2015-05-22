package com.guesswhat.android.service.rs.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.guesswhat.android.service.rs.face.DatabaseService;
import com.guesswhat.android.service.utils.ServiceUtils;

public class DatabaseServiceImpl implements DatabaseService {

    @Override
    public Integer getVersion() {
    	String databaseUrl = ServiceUtils.getDatabaseUrl();
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(databaseUrl);
		Response response = null;
		
		Builder invocationBuilder = webTarget.path("version").request();
		invocationBuilder.header(HttpHeaders.AUTHORIZATION, ServiceUtils.getAuthorization());
		
		response = invocationBuilder.post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE));
		int version = response.readEntity(Integer.class);
		
		return version;
    }

}
