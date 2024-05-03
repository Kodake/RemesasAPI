package com.remesasapi.servicio;

import com.remesasapi.modelo.Tasa;
import com.remesasapi.repositorio.ITasaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasaServicio implements ITasaServicio {
    @Autowired
    private ITasaRepositorio tasaRepositorio;

    @Override
    public Page<Tasa> listarPaginado(Pageable pageable) {
        return tasaRepositorio.findAll(pageable);
    }

    @Override
    public List<Tasa> listar() {
        return tasaRepositorio.findAll();
    }

    @Override
    public Tasa buscarPorId(Integer idTasa) {
        return tasaRepositorio.findById(idTasa).orElse(null);
    }

    @Override
    public Tasa guardar(Tasa tasa) {
        return tasaRepositorio.save(tasa);
    }
}
