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

import br.com.Leonardo.Pet.Entity.Dono;
import br.com.Leonardo.Pet.Repository.DonoRepository;

@RestController
@RequestMapping("/donos")
public class DonoController {

    private final DonoRepository donoRepository;

    @Autowired
    public DonoController(DonoRepository donoRepository) {
        this.donoRepository = donoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Dono>> getAllDonos() {
        List<Dono> donos = donoRepository.findAll();
        return new ResponseEntity<>(donos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dono> getDonoById(@PathVariable("id") Long id) {
        return donoRepository.findById(id)
                .map(dono -> new ResponseEntity<>(dono, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Dono> createDono(@RequestBody Dono dono) {
        Dono savedDono = donoRepository.save(dono);
        return new ResponseEntity<>(savedDono, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dono> updateDono(@PathVariable("id") Long id, @RequestBody Dono donoDetails) {
        return donoRepository.findById(id)
                .map(dono -> {
                    dono.setNome(donoDetails.getNome());
                    dono.setIdade(donoDetails.getIdade());
                    Dono updatedDono = donoRepository.save(dono);
                    return new ResponseEntity<>(updatedDono, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDono(@PathVariable("id") Long id) {
        try {
            donoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}