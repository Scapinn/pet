package br.com.Leonardo.Pet.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.Leonardo.Pet.Entity.Animal;
import br.com.Leonardo.Pet.Repository.AnimalRepository;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping
    public ResponseEntity<List<Animal>> getAllAnimais() {
        List<Animal> animais = animalRepository.findAll();
        return new ResponseEntity<>(animais, HttpStatus.OK);
    }

    @GetMapping("/dono/{donoId}")
    public ResponseEntity<List<Animal>> getAnimaisByDonoId(@PathVariable("donoId") Long donoId) {
        List<Animal> animais = animalRepository.findByDonoId(donoId);
        return new ResponseEntity<>(animais, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable("id") Long id) {
        return animalRepository.findById(id)
                .map(animal -> new ResponseEntity<>(animal, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal) {
        Animal savedAnimal = animalRepository.save(animal);
        return new ResponseEntity<>(savedAnimal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable("id") Long id, @RequestBody Animal animalDetails) {
        return animalRepository.findById(id)
                .map(animal -> {
                    animal.setNome(animalDetails.getNome());
                    animal.setIdade(animalDetails.getIdade());
                    animal.setRaca(animalDetails.getRaca());
                    animal.setDono(animalDetails.getDono());
                    Animal updatedAnimal = animalRepository.save(animal);
                    return new ResponseEntity<>(updatedAnimal, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAnimal(@PathVariable("id") Long id) {
        try {
            animalRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}