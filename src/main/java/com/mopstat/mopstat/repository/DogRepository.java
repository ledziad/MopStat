package com.mopstat.mopstat.repository;
import com.mopstat.mopstat.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Long> {
    List<Dog> findByUserId(Long userId);
}
