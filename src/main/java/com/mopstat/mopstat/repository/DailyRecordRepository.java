package com.mopstat.mopstat.repository;

import com.mopstat.mopstat.model.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {
    List<DailyRecord> findByDogId(Long dogId);
}
