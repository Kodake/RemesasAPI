package com.remesasapi.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RecursoSolicitudFallidaExcepcion extends RuntimeException {
    public RecursoSolicitudFallidaExcepcion(String mensaje) {
        super(mensaje);
    }
}
