package com.mopstat.mopstat.repository;
import com.mopstat.mopstat.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
