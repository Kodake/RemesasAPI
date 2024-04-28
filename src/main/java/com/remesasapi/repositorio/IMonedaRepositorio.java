package com.remesasapi.repositorio;

import com.remesasapi.modelo.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMonedaRepositorio extends JpaRepository<Moneda, Integer> {
    Moneda findByCodigo(String codigo);
}
