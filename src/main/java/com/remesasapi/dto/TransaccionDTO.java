package com.remesasapi.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TransaccionDTO {
    Long idTransaccion;
    String codigo;
    Integer clienteOrigen;
    Integer clienteDestino;
    Double cantidad;
    Integer moneda;
    LocalDate fecha;
    boolean retirado;
}
