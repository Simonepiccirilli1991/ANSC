package com.ansc.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ansc.model.Anagrafica;

@Repository
public interface AnagraficaRepo extends JpaRepository<Anagrafica, Long>{

	@Query(value = "SELECT * FROM anagrafica WHERE anagrafica.bt = :bt",
			nativeQuery = true)
	Anagrafica findByBt(@Param("bt") String bt);
}
