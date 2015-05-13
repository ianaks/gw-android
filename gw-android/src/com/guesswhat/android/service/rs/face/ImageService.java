package com.guesswhat.android.service.rs.face;

import com.guesswhat.android.system.utils.ImageType;

public interface ImageService {
    byte [] findQuestionImage(String questionId, ImageType imageType);
    byte [] findAnswerImage(String questionId, ImageType imageType);
}
