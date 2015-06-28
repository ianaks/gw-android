package com.guesswhat.android.game.utils;

import com.appodeal.ads.Appodeal;

import android.app.Activity;

public class AdController {

	public static void init(Activity activity) {
    	String appKey = "8b587b841168c98364d21a98d1d2fb43d47ab5b7d76637fc";
        Appodeal.initialize(activity, appKey, Appodeal.INTERSTITIAL);
        Appodeal.setTesting(true);
	}
	
	public static void showImage(Activity activity) {
		Appodeal.show(activity, Appodeal.INTERSTITIAL);
	}
	
}
