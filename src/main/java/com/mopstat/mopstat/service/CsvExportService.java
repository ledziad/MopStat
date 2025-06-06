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
import java.util.ArrayList;
import com.mopstat.mopstat.dto.DogDTO;
@Service
public class CsvExportService {

    private final DailyRecordService dailyRecordService;
    private final DogService dogService;

    public CsvExportService(DailyRecordService dailyRecordService, DogService dogService)
    {
        this.dailyRecordService = dailyRecordService;
        this.dogService = dogService;
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
    public byte[] exportAllUserRecords(String username) {
        List<DogDTO> dogs = dogService.getDogsForUser(username);
        List<DailyRecordDTO> allRecords = new ArrayList<>();
        for (DogDTO dog : dogs) {
            allRecords.addAll(dailyRecordService.getRecordsForDog(dog.getId()));
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Dodaj BOM UTF-8 (Excel-friendly)
            baos.write(0xEF);
            baos.write(0xBB);
            baos.write(0xBF);

            try (OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                         .withHeader("ID Psa", "Imię psa", "Data", "Ilość posiłków", "Kupki", "Spacery", "Notka nastroju"))) {

                for (DailyRecordDTO record : allRecords) {
                    DogDTO dog = dogs.stream().filter(d -> d.getId().equals(record.getDogId())).findFirst().orElse(null);
                    csvPrinter.printRecord(
                            record.getDogId(),
                            dog != null ? dog.getName() : "",
                            record.getDate(),
                            record.getMeals(),
                            record.getPoops(),
                            record.getWalks(),
                            record.getMoodNote()
                    );
                }
                csvPrinter.flush();
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Nie udało się wygenerować pliku CSV", e);
        }
    }

}
