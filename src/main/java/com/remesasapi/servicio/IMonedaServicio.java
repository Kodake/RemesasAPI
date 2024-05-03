package com.remesasapi.servicio;

import com.remesasapi.modelo.Moneda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMonedaServicio {
    public Page<Moneda> listarPaginado(Pageable pageable);
    public List<Moneda> listar();
    public Moneda buscarPorId(Integer idMoneda);
    public Moneda buscarPorCodigo(String codigo);
    public Moneda guardar(Moneda moneda);
    public void eliminar(Moneda moneda);
}
