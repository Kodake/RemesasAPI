package com.remesasapi.servicio;

import com.remesasapi.modelo.Transaccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITransaccionServicio {
    public Page<Transaccion> listarPaginado(Pageable pageable);
    public Transaccion buscarPorId(Long idTransaccion);
    public Transaccion buscarPorCodigo(String codigo);
    public Transaccion guardar(Transaccion transaccion);
}
