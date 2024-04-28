package com.remesasapi.servicio;

import com.remesasapi.modelo.Cliente;
import com.remesasapi.repositorio.IClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio implements IClienteServicio {
    @Autowired
    private IClienteRepositorio clienteRepositorio;

    @Override
    public Page<Cliente> listarPaginado(Pageable pageable) {
        return clienteRepositorio.findAll(pageable);
    }

    @Override
    public Cliente buscarPorId(Integer idCliente) {
        return clienteRepositorio.findById(idCliente).orElse(null);
    }

    public Cliente buscarPorDocumento(String documento) {
        return clienteRepositorio.findByDocumento(documento);
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }
}
