package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.dto.DogDTO;
import com.mopstat.mopstat.service.CsvExportService;
import com.mopstat.mopstat.service.DogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/dogs")
@Validated
public class DogController {

    private final DogService dogService;
    private final CsvExportService csvExportService;

    public DogController(DogService dogService,
                         CsvExportService csvExportService) {
        this.dogService = dogService;
        this.csvExportService = csvExportService;
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
}
