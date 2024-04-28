package com.remesasapi.servicio;

import com.remesasapi.modelo.Moneda;
import com.remesasapi.repositorio.IMonedaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MonedaServicio implements IMonedaServicio {
    @Autowired
    private IMonedaRepositorio monedaRepositorio;

    @Override
    public Page<Moneda> listarPaginado(Pageable pageable) {
        return monedaRepositorio.findAll(pageable);
    }

    @Override
    public Moneda buscarPorId(Integer idMoneda) {
        Moneda moneda = (Moneda) monedaRepositorio.findById(idMoneda).orElse(null);
        return moneda;
    }

    @Override
    public Moneda buscarPorCodigo(String codigo) {
        return monedaRepositorio.findByCodigo(codigo);
    }

    @Override
    public Moneda guardar(Moneda moneda) {
       return  monedaRepositorio.save(moneda);
    }

    @Override
    public void eliminar(Moneda moneda) {
        monedaRepositorio.delete(moneda);
    }
}
