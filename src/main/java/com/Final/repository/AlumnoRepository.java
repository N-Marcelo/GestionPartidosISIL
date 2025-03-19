package com.Final.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Final.entity.*;

public interface AlumnoRepository extends JpaRepository<Alumno,Integer>{
	List<Alumno> findAll();
}
