package com.remesasapi.repositorio;

import com.remesasapi.modelo.Tasa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITasaRepositorio extends JpaRepository<Tasa, Integer> {
}
