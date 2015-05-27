package com.guesswhat.android.service.rs.impl;

import com.guesswhat.android.service.cfg.RestWebClient;
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
    	String imageUrl = ServiceUtils.getImageUrl() + "find" + path + questionId + type;
    	
    	RestWebClient client = RestWebClient.getClient();
    	byte[] bytes = client.postForObject(imageUrl, null, byte [].class);
		
		return bytes;
    }

}
