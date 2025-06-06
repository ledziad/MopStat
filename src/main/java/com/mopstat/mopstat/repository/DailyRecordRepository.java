package com.mopstat.mopstat.repository;

import com.mopstat.mopstat.model.DailyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyRecordRepository extends JpaRepository<DailyRecord, Long> {
    // Zwraca wpisy dla konkretnego psa
    List<DailyRecord> findByDogId(Long dogId);

    // NOWA METODA: Zwraca wpisy wszystkich psów należących do konkretnego użytkownika
    List<DailyRecord> findAllByDog_User_Id(Long userId);
void deleteAllByDogId(Long dogId);
}

