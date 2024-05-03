package com.remesasapi.servicio;

import com.remesasapi.modelo.Tasa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITasaServicio {
    public Page<Tasa> listarPaginado(Pageable pageable);
    public List<Tasa> listar();
    public Tasa buscarPorId(Integer idTasa);
    public Tasa guardar(Tasa tasa);
}
