package com.Final.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Final.entity.*;


@Repository
public interface PartidoPoliticoRepository extends JpaRepository<PartidoPolitico,Integer>{
	List<PartidoPolitico> findAll();
	List<PartidoPolitico> findByNombreContaining(String nombre);
	void deleteById(int id);
	PartidoPolitico findById(int id);
	PartidoPolitico findByNombre(String nombre);

}
