package com.remesasapi.controlador;

import com.remesasapi.excepcion.RecursoNoEncontradoExcepcion;
import com.remesasapi.modelo.Cliente;
import com.remesasapi.modelo.Moneda;
import com.remesasapi.servicio.IMonedaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/remesas")
@CrossOrigin(value = "http://localhost:5173")
public class MonedaControlador {
    @Autowired
    private IMonedaServicio monedaServicio;

    @GetMapping("/monedas")
    public Page<Moneda> listarPaginado(@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return monedaServicio.listarPaginado(pageable);
    }

    @GetMapping("/monedas/{id}")
    public ResponseEntity<Moneda> buscarPorId(@PathVariable Integer id) {
        Moneda moneda = monedaServicio.buscarPorId(id);
        if (moneda == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro la moneda con el id: " + id);
        }
        return ResponseEntity.ok(moneda);
    }

    @PostMapping("/monedas")
    public ResponseEntity<?> agregar(@RequestBody Moneda moneda) {
        Moneda monedaExistente = monedaServicio.buscarPorCodigo(moneda.getCodigo());
        if (monedaExistente != null) {
            String mensajeDeError = "Ya existe una moneda con el código: " + monedaExistente.getCodigo();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }
        Moneda monedaGuardada = monedaServicio.guardar(moneda);
        return ResponseEntity.ok(monedaGuardada);
    }

    @PutMapping("/monedas/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Moneda monedaActualizada) {
        Moneda monedaExistente = monedaServicio.buscarPorId(id);
        if (monedaExistente == null) {
            String mensajeDeError = "No se encontro la moneda con el id: " + id;
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }

        Moneda monedaConCodigoExistente = monedaServicio.buscarPorCodigo(monedaActualizada.getCodigo());
        if (monedaConCodigoExistente != null && !monedaConCodigoExistente.getIdMoneda().equals(id)) {
            String mensajeDeError = "Ya existe una moneda con el código: " + monedaActualizada.getCodigo();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }
        monedaExistente.setNombre(monedaActualizada.getNombre());
        monedaExistente.setCodigo(monedaActualizada.getCodigo());
        monedaServicio.guardar(monedaExistente);
        return ResponseEntity.ok(monedaExistente);
    }

    @DeleteMapping("/monedas/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminar(@PathVariable Integer id) {
        Moneda moneda = monedaServicio.buscarPorId(id);
        if (moneda == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró la moneda con el id: " + id);
        }
        monedaServicio.eliminar(moneda);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
