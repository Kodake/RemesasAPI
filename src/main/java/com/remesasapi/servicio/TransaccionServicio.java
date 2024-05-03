package com.remesasapi.servicio;

import com.remesasapi.modelo.Transaccion;
import com.remesasapi.repositorio.ITransaccionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransaccionServicio implements ITransaccionServicio {
    @Autowired
    private ITransaccionRepositorio transaccionRepositorio;

    @Override
    public Page<Transaccion> listarPaginado(Pageable pageable) {
        return transaccionRepositorio.findAll(pageable);
    }

    @Override
    public Transaccion buscarPorId(Long idTransaccion) {
        return transaccionRepositorio.findById(idTransaccion).orElse(null);
    }

    @Override
    public Transaccion buscarPorCodigo(String codigo) {
        return transaccionRepositorio.findByCodigo(codigo);
    }

    @Override
    public Transaccion guardar(Transaccion transaccion) {
        return transaccionRepositorio.save(transaccion);
    }
}
