package com.guesswhat.android.service.rs.impl;

import com.guesswhat.android.service.rs.dto.RecordDTO;
import com.guesswhat.android.service.rs.face.RecordService;

import java.util.List;

public class RecordServiceImpl implements RecordService {

    @Override
    public void saveUserRecord(RecordDTO recordDTO) {

    }

    @Override
    public List<RecordDTO> findTopRecords() {
        return null;
    }

    @Override
    public int findUserPlace(String userId) {
        return 0;
    }

}