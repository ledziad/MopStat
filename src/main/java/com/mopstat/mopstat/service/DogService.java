package com.mopstat.mopstat.service;

import com.mopstat.mopstat.dto.DogDTO;
import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.repository.DogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public List<DogDTO> getAll() {
        return dogRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DogDTO create(DogDTO dto) {
        Dog dog = new Dog(
                dto.getName(),
                dto.getPersonality(),
                dto.getImagePath()
        );
        Dog saved = dogRepository.save(dog);
        return toDTO(saved);
    }

    private DogDTO toDTO(Dog dog) {
        return new DogDTO(
                dog.getId(),
                dog.getName(),
                dog.getPersonality(),
                dog.getImagePath()
        );
    }
}
