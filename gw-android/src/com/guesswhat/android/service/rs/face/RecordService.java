package com.guesswhat.android.service.rs.face;

import com.guesswhat.android.service.rs.dto.RecordDTO;

import java.util.List;

public interface RecordService {
	
    void saveUserRecord(RecordDTO recordDTO);
    
    List<RecordDTO> findTopRecords();
    
    Integer findUserPlace(String userId);
    
}
