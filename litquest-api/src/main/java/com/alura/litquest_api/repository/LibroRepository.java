package com.alura.litquest_api.repository;

import com.alura.litquest_api.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro,Long> {
}
