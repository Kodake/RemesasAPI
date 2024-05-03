package com.remesasapi.dto;

import lombok.Data;

@Data
public class TasaDTO {
    Integer idTasa;
    Integer monedaOrigen;
    Integer monedaDestino;
    Double valor;
}
