package com.remesasapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetTransaccionDTO {
    Long idTransaccion;
    String codigo;
    String clienteDestino;
    String moneda;
    Double cantidad;
    LocalDate fecha;
    boolean retirado;
}
