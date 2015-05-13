package com.guesswhat.android.service.rs.impl;

import com.guesswhat.android.service.rs.face.ImageService;
import com.guesswhat.android.system.utils.ImageType;

public class ImageServiceImpl implements ImageService {

    @Override
    public byte[] findQuestionImage(String questionId, ImageType imageType) {
        return new byte[0];
    }

    @Override
    public byte[] findAnswerImage(String questionId, ImageType imageType) {
        return new byte[0];
    }

}
