package com.remesasapi.repositorio;

import com.remesasapi.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {
    Cliente findByDocumento(String documento);
}
