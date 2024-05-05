package com.remesasapi.controlador;

import com.remesasapi.modelo.Cliente;
import com.remesasapi.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/remesas")
@CrossOrigin(value = "http://localhost:5173")
public class ClienteControlador {
    @Autowired
    private IClienteServicio clienteServicio;

    @GetMapping("/clientes")
    public Page<Cliente> listarPaginado(@RequestParam(defaultValue = "0") int pageNumber,
                                       @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return clienteServicio.listarPaginado(pageable);
    }

    @GetMapping("/clientes/listar")
    public List<Cliente> listar() {
        return clienteServicio.listar();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Cliente cliente = clienteServicio.buscarPorId(id);
        if (cliente == null) {
            String mensajeDeError = "No se encontro el cliente con el id: " + id;
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> agregar(@RequestBody Cliente cliente) {
        Cliente clienteExistente = clienteServicio.buscarPorDocumento(cliente.getDocumento());
        if (clienteExistente != null) {
            String mensajeDeError = "Ya existe un cliente con el documento: " + clienteExistente.getDocumento();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }
        Cliente clienteGuardado = clienteServicio.guardar(cliente);
        return ResponseEntity.ok(clienteGuardado);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Cliente clienteActualizado) {
        Cliente clienteExistente = clienteServicio.buscarPorId(id);
        if (clienteExistente == null) {
            String mensajeDeError = "No se encontro el cliente con el id: " + id;
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }

        Cliente clienteConDocumentoExistente = clienteServicio.buscarPorDocumento(clienteActualizado.getDocumento());
        if (clienteConDocumentoExistente != null && !clienteConDocumentoExistente.getIdCliente().equals(id)) {
            String mensajeDeError = "Ya existe un cliente con el documento: " + clienteActualizado.getDocumento();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }
        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setApellido(clienteActualizado.getApellido());
        clienteExistente.setDocumento(clienteActualizado.getDocumento());
        clienteExistente.setTipo(clienteActualizado.getTipo());
        clienteServicio.guardar(clienteExistente);
        return ResponseEntity.ok(clienteExistente);
    }
}
