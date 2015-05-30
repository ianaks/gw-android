package com.guesswhat.android.service.cfg;

import java.util.Collections;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestWebClient extends RestTemplate {
	
	private static RestWebClient instance;
	
	private RestWebClient() {
		getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		ClientHttpRequestInterceptor authorizationInterceptor = new AuthorizationInterceptor();
		setInterceptors(Collections.singletonList(authorizationInterceptor));
	}
	
	public static RestWebClient getClient() {
		if (instance == null) {
			instance = new RestWebClient();
		}
		return instance;
	}
}
