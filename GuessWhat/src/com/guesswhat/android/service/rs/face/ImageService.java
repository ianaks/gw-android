package com.guesswhat.android.service.rs.face;

public interface ImageService {
    byte [] findQuestionImage(Long questionId, String imageType);
    byte [] findAnswerImage(Long questionId, String imageType);
}
