package com.remesasapi.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;
    @ManyToOne
    @JoinColumn(name = "id_cliente_origen", referencedColumnName = "idCliente")
    private Cliente clienteOrigen;
    @ManyToOne
    @JoinColumn(name = "id_cliente_destino", referencedColumnName = "idCliente")
    private Cliente clienteDestino;
    private Double cantidad;
    @ManyToOne
    @JoinColumn(name = "id_moneda", referencedColumnName = "idMoneda")
    private Moneda moneda;
    private LocalDate fecha;
    private boolean esRetirado;
}
