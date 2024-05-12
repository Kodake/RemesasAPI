package com.remesasapi.repositorio;

import com.remesasapi.modelo.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransaccionRepositorio extends JpaRepository<Transaccion, Long> {
    Transaccion findByCodigo(String codigo);
}
