package com.guesswhat.android.service.utils;

import java.nio.charset.Charset;

import android.util.Base64;

import com.guesswhat.android.system.utils.SystemProperties;

public class ServiceUtils {

	public static String getAuthorization() {
		return new String(Base64.encode(String.valueOf(
						SystemProperties.AUTH_LOGIN + ":"
								+ SystemProperties.AUTH_PASSWORD).getBytes(), Base64.NO_WRAP),
						Charset.forName("ASCII"));
	}
	
	public static String getRecordUrl() {
		return SystemProperties.SERVER_URL + "/records";
	}
	
	public static String getQuestionUrl() {
		return SystemProperties.SERVER_URL + "/questions";
	}
	
	public static String getImageUrl() {
		return SystemProperties.SERVER_URL + "/images";
	}
	
	public static String getDatabaseUrl() {
		return SystemProperties.SERVER_URL + "/database";
	}

}
