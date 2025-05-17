package com.mopstat.mopstat.service;

import com.mopstat.mopstat.dto.DailyRecordDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.mopstat.mopstat.dto.DogDTO;
@Service
public class CsvExportService {

    private final DailyRecordService dailyRecordService; // Dodaj tę zależność przez konstruktor

    public CsvExportService(DailyRecordService dailyRecordService) {
        this.dailyRecordService = dailyRecordService;
    }

    /**
     * Eksportuje wszystkie DailyRecord dla danego psa do CSV.
     */
    public byte[] exportDogRecords(Long dogId) {
        List<DailyRecordDTO> records = dailyRecordService.getRecordsForDog(dogId);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("ID", "Data", "Ilość posiłków", "Kupki", "Spacery", "Notka nastroju"))) {

            for (DailyRecordDTO record : records) {
                csvPrinter.printRecord(
                        record.getId(),
                        record.getDate(),
                        record.getMeals(),
                        record.getPoops(),
                        record.getWalks(),
                        record.getMoodNote()
                );
            }
            csvPrinter.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Nie udało się wygenerować pliku CSV", e);
        }
    }
}