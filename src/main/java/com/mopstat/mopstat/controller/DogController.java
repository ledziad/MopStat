package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.dto.DailyRecordDTO;
import com.mopstat.mopstat.dto.DogDTO;
import com.mopstat.mopstat.service.CsvExportService;
import com.mopstat.mopstat.service.DailyRecordService;
import com.mopstat.mopstat.service.DogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/dogs")
@Validated
public class DogController {

    private final DogService dogService;
    private final CsvExportService csvExportService;
    private final DailyRecordService dailyRecordService;

    public DogController(DogService dogService,
                         CsvExportService csvExportService, DailyRecordService dailyRecordService) {
        this.dogService = dogService;
        this.csvExportService = csvExportService;
        this.dailyRecordService = dailyRecordService;
    }

    // GET /api/dogs → lista DTO
    @GetMapping
    public List<DogDTO> getAll() {
        return dogService.getAll();
    }

    // POST /api/dogs → tworzenie z DTO, zwraca 201 + Location
    @PostMapping
    public ResponseEntity<DogDTO> create(@Valid @RequestBody DogDTO dto) {
        DogDTO created = dogService.create(dto);

        // zwracamy tylko względną ścieżkę, żeby test przeszedł
        URI location = URI.create("/api/dogs/" + created.getId());

        return ResponseEntity
                .created(location)
                .body(created);
    }


    // GET /api/dogs/{id}/export → eksport CSV historii psa
    @GetMapping(value = "/{id}/export", produces = "text/csv")
    public ResponseEntity<byte[]> exportCsv(@PathVariable Long id) throws Exception {
        byte[] csvData = csvExportService.exportDogRecords(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"dog-" + id + "-records.csv\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvData);
    }
//    @GetMapping("/{id}/records")
//    public List<DailyRecordDTO> getRecordsForDog(@PathVariable Long id) {
//        return dailyRecordService.getRecordsForDog(id);
//    }
    @GetMapping("/{id}")
    public DogDTO getById(@PathVariable Long id) {
        return dogService.getById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDog(@PathVariable Long id, Principal principal) {
        dogService.deleteDogById(id, principal.getName());
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<DogDTO> updateDog(
            @PathVariable Long id,
            @RequestBody DogDTO dto,
            Authentication authentication
    ) {
        String username = authentication.getName();
        DogDTO updated = dogService.update(id, dto, username);
        return ResponseEntity.ok(updated);
    }


}
