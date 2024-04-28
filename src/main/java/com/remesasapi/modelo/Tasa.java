package com.remesasapi.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tasa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idTasa;
    @ManyToOne
    @JoinColumn(name = "id_moneda_origen", referencedColumnName = "idMoneda")
    private Moneda monedaOrigen;
    @ManyToOne
    @JoinColumn(name = "id_moneda_destino", referencedColumnName = "idMoneda")
    private Moneda monedaDestino;
    Double valor;
}
