package com.remesasapi.servicio;

import com.remesasapi.modelo.Tasa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITasaServicio {
    public Page<Tasa> listarPaginado(Pageable pageable);
    public Tasa buscarPorId(Integer idTasa);
    public Tasa guardar(Tasa tasa);
}
