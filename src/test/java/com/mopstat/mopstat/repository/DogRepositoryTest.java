package com.mopstat.mopstat.repository;

import com.mopstat.mopstat.model.Dog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
@DataJpaTest   (properties = "spring.sql.init.mode=never")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class DogRepositoryTest {

    @Autowired
    private DogRepository dogRepository;

    @Test
    @DisplayName("findAll() zwraca zapisane encje")
    void shouldFindAllDogs() {
        // baza jest pusta, bo rollback z poprzedniego testu
        var dog1 = new Dog("Test1", "Charakterny", "/img/1.png");
        var dog2 = new Dog("Test2", "Spokojny", "/img/2.png");
        dogRepository.saveAll(List.of(dog1, dog2));

        var all = dogRepository.findAll();
        assertThat(all).hasSize(2)
                .extracting(Dog::getName)
                .containsExactlyInAnyOrder("Test1", "Test2");
    }

    @Test
    @DisplayName("findById() działa poprawnie")
    void shouldFindById() {
        // też czysta baza na start
        var dog = new Dog("Solo", "Samotnik", "/img/solo.png");
        Dog saved = dogRepository.save(dog);

        var fetched = dogRepository.findById(saved.getId());
        assertThat(fetched).isPresent()
                .get()
                .extracting(Dog::getName)
                .isEqualTo("Solo");
    }
}

