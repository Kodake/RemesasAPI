package com.remesasapi.servicio;

import com.remesasapi.modelo.Moneda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMonedaServicio {
    public Page<Moneda> listarPaginado(Pageable pageable);
    public Moneda buscarPorId(Integer idMoneda);
    public Moneda buscarPorCodigo(String codigo);
    public Moneda guardar(Moneda moneda);
    public void eliminar(Moneda moneda);
}
