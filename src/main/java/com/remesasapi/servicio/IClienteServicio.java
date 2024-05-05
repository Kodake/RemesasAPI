package com.remesasapi.servicio;

import com.remesasapi.modelo.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteServicio {
    public List<Cliente> listar();
    public Page<Cliente> listarPaginado(Pageable pageable);
    public Cliente buscarPorId(Integer idCliente);
    public Cliente buscarPorDocumento(String documento);
    public Cliente guardar(Cliente cliente);
}
