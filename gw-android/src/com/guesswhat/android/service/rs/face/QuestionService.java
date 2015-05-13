package com.guesswhat.android.service.rs.face;

import com.guesswhat.android.service.rs.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {
    QuestionDTO findQuestion(Long questionId);
    List<QuestionDTO> findQuestions();
}
