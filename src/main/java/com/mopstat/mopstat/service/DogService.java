package com.mopstat.mopstat.service;

import com.mopstat.mopstat.dto.DogDTO;
import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.model.User;
import com.mopstat.mopstat.repository.DogRepository;
import com.mopstat.mopstat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {
    private final DogRepository dogRepository;
    private final UserRepository userRepository;

    @Autowired
    public DogService(DogRepository dogRepository, UserRepository userRepository) {
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
    }

    // Pobiera psy tylko aktualnego usera!
    public List<DogDTO> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return dogRepository.findByUserId(user.getId()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Tworzy psa, przypisując go do aktualnego usera!
    public DogDTO create(DogDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Dog dog = new Dog(
                dto.getName(),
                dto.getPersonality(),
                dto.getImagePath(),
                user // nowy pies jest przypisany do usera!
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
