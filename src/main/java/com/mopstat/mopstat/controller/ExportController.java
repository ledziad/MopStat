package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.service.CsvExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExportController {

    private final CsvExportService csvExportService;

    @Autowired
    public ExportController(CsvExportService csvExportService) {
        this.csvExportService = csvExportService;
    }

    @GetMapping("/api/export/csv")
    public ResponseEntity<byte[]> exportAllCsv(Authentication authentication) {
        String username = authentication.getName(); // lub userId, jeśli masz w JWT!
        byte[] csv = csvExportService.exportAllUserRecords(username);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mopstat-eksport.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }
}
