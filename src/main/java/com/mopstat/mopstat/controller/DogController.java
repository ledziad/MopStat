package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.repository.DogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dogs")
public class DogController {

    private final DogRepository dogRepository;

    public DogController(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @GetMapping
    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }
}
