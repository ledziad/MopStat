package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.dto.DailyRecordDTO;
import com.mopstat.mopstat.service.DailyRecordService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class DailyRecordController {

    private final DailyRecordService dailyRecordService;

    @Autowired
    public DailyRecordController(DailyRecordService dailyRecordService) {
        this.dailyRecordService = dailyRecordService;
    }

    // GET /api/records – lista wpisów dla zalogowanego użytkownika
    @GetMapping("/records")
    public ResponseEntity<List<DailyRecordDTO>> getAllRecordsForUser() {
        List<DailyRecordDTO> records = dailyRecordService.getAllForCurrentUser();
        return ResponseEntity.ok(records);
    }

    // POST /api/records – dodaj nowy wpis dzienny (ogólny, z dogId w body)
    @PostMapping("/records")
    public ResponseEntity<DailyRecordDTO> addRecord(@RequestBody DailyRecordDTO recordDTO) {
        DailyRecordDTO saved = dailyRecordService.addRecord(recordDTO);
        return ResponseEntity.ok(saved);
    }

    // *** POPRAWKA: POST /api/dogs/{dogId}/records ***
    @PostMapping("/dogs/{dogId}/records")
    public ResponseEntity<DailyRecordDTO> addRecordForDog(
            @PathVariable Long dogId,
            @RequestBody DailyRecordDTO recordDTO
    ) {
        // Nadpisz dogId, żeby nie było niespójności (zawsze bierze z URL)
        recordDTO.setDogId(dogId);
        DailyRecordDTO saved = dailyRecordService.addRecord(recordDTO);
        return ResponseEntity.ok(saved);
    }

    // GET /api/dogs/{dogId}/records – historia wpisów dziennych tego psa
    @GetMapping("/dogs/{dogId}/records")
    public ResponseEntity<List<DailyRecordDTO>> getRecordsForDog(@PathVariable Long dogId) {
        List<DailyRecordDTO> records = dailyRecordService.getRecordsForDog(dogId);
        return ResponseEntity.ok(records);
    }
    @DeleteMapping("/dogs/{dogId}/records/{recordId}")
    public ResponseEntity<?> deleteRecord(
            @PathVariable Long dogId,
            @PathVariable Long recordId,
            Principal principal
    ) {
        dailyRecordService.deleteRecord(dogId, recordId, principal.getName());
        return ResponseEntity.ok().build();
    }
    @PutMapping("/dogs/{dogId}/records/{recordId}")
    public DailyRecordDTO editRecord(
            @PathVariable Long dogId,
            @PathVariable Long recordId,
            @RequestBody DailyRecordDTO dto,
            Principal principal
    ) {
        return dailyRecordService.editRecord(dogId, recordId, dto, principal.getName());
    }

}
