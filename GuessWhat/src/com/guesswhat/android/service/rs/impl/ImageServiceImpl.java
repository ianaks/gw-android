package com.guesswhat.android.service.rs.impl;

import com.guesswhat.android.service.rs.face.ImageService;

public class ImageServiceImpl implements ImageService {

    @Override
    public byte[] findQuestionImage(Long questionId, String imageType) {
        return new byte[0];
    }

    @Override
    public byte[] findAnswerImage(Long questionId, String imageType) {
        return new byte[0];
    }

}
