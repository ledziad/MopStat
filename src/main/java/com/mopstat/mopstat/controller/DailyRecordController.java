package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.dto.DailyRecordDTO;
import com.mopstat.mopstat.service.DailyRecordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@SecurityRequirement(name = "bearerAuth")
public class DailyRecordController {

    private final DailyRecordService dailyRecordService;

    @Autowired
    public DailyRecordController(DailyRecordService dailyRecordService) {
        this.dailyRecordService = dailyRecordService;
    }

    // GET /api/records – lista wpisów dla zalogowanego użytkownika
    @GetMapping
    public ResponseEntity<List<DailyRecordDTO>> getAllRecordsForUser() {
        // Pobieranie po userId z JWT - do implementacji!
        List<DailyRecordDTO> records = dailyRecordService.getAllForCurrentUser();
        return ResponseEntity.ok(records);
    }

    // POST /api/records – dodaj nowy wpis dzienny
    @PostMapping
    public ResponseEntity<DailyRecordDTO> addRecord(@RequestBody DailyRecordDTO recordDTO) {
        DailyRecordDTO saved = dailyRecordService.addRecord(recordDTO);
        return ResponseEntity.ok(saved);
    }
}
