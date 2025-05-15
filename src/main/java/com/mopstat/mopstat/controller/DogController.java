package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.dto.DogDTO;
import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.repository.DogRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;  // opcjonalnie na klasie


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dogs")
@Validated            // pozwala na walidację metod na poziomie kontrolera

public class DogController {

    private final DogRepository dogRepository;

    public DogController(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    // KONWERTERY:
    private static DogDTO toDTO(Dog dog) {
        return new DogDTO(
                dog.getId(),
                dog.getName(),
                dog.getPersonality(),
                dog.getImagePath()
        );
    }

    private static Dog toEntity(DogDTO dto) {
        Dog dog = new Dog();
        dog.setId(dto.getId());
        dog.setName(dto.getName());
        dog.setPersonality(dto.getPersonality());
        dog.setImagePath(dto.getImagePath());
        return dog;
    }

    // GET  /api/dogs → lista DTO
    @GetMapping
    public List<DogDTO> getAll() {
        return dogRepository.findAll()
                .stream()
                .map(DogController::toDTO)
                .collect(Collectors.toList());
    }

    // POST /api/dogs → tworzenie z DTO
    @PostMapping
    public DogDTO create( @Valid @RequestBody DogDTO dto) {
        Dog saved = dogRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    // (opcjonalnie) PUT / DELETE też z DTO...
}