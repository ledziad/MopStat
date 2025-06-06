package com.mopstat.mopstat.repository;

import com.mopstat.mopstat.model.User;

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
import com.mopstat.mopstat.model.User;
import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.repository.UserRepository;
import com.mopstat.mopstat.repository.DogRepository;

// ... inne importy

@DataJpaTest(properties = "spring.sql.init.mode=never")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class DogRepositoryTest {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findAll() zwraca zapisane encje")
    void shouldFindAllDogs() {
        var user = userRepository.save(new User("testuser", "test@email.com", "haslo123"));

        var dog1 = new Dog("Test1", "Charakterny", "/img/1.png", user);
        var dog2 = new Dog("Test2", "Spokojny", "/img/2.png", user);
        dogRepository.saveAll(List.of(dog1, dog2));

        var all = dogRepository.findAll();
        assertThat(all).hasSize(2)
                .extracting(Dog::getName)
                .containsExactlyInAnyOrder("Test1", "Test2");
    }

    @Test
    @DisplayName("findById() działa poprawnie")
    void shouldFindById() {
        var user = userRepository.save(new User("testuser", "test@email.com", "haslo123"));

        var dog = new Dog("Solo", "Samotnik", "/img/solo.png", user);
        Dog saved = dogRepository.save(dog);

        var fetched = dogRepository.findById(saved.getId());
        assertThat(fetched).isPresent()
                .get()
                .extracting(Dog::getName)
                .isEqualTo("Solo");
    }
}
