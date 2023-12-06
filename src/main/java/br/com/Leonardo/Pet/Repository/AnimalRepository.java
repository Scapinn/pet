package br.com.Leonardo.Pet.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Leonardo.Pet.Entity.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByDonoId(Long donoId);
}