package com.remesasapi.repositorio;

import com.remesasapi.modelo.Cliente;
import com.remesasapi.modelo.Transaccion;
import jakarta.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransaccionRepositorio extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByClienteDestinoAndEsRetiradoIsFalse(Cliente clienteDestino);
}
