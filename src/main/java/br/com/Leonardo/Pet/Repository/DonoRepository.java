package br.com.Leonardo.Pet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Leonardo.Pet.Entity.Dono;

@Repository
	public interface DonoRepository extends JpaRepository<Dono, Long> {
	    
	}


