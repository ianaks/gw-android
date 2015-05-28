package com.guesswhat.android.service.cfg;

import java.util.Collections;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestWebClient extends RestTemplate {
	
	private static RestWebClient instance;
	
	private RestWebClient() {
		getMessageConverters().add(new StringHttpMessageConverter());
		setInterceptors(Collections.singletonList(new AuthorizationInterceptor()));
	}
	
	public static RestWebClient getClient() {
		if (instance == null) {
			instance = new RestWebClient();
		}
		return instance;
	}
}
