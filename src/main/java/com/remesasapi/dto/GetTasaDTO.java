package com.remesasapi.dto;

import lombok.Data;

@Data
public class GetTasaDTO {
    Integer idTasa;
    String monedaOrigen;
    String monedaDestino;
    Double valor;
}
