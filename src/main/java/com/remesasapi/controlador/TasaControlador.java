package com.remesasapi.controlador;

import com.remesasapi.excepcion.RecursoNoEncontradoExcepcion;
import com.remesasapi.modelo.Tasa;
import com.remesasapi.servicio.ITasaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/remesas")
@CrossOrigin(value = "http://localhost:5173")
public class TasaControlador {
    @Autowired
    private ITasaServicio tasaServicio;

    @GetMapping("/tasas")
    public Page<Tasa> listarPaginado(@RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return tasaServicio.listarPaginado(pageable);
    }

    @GetMapping("/tasas/{id}")
    public ResponseEntity<Tasa> buscarPorId(@PathVariable Integer id) {
        Tasa tasa = tasaServicio.buscarPorId(id);
        if (tasa == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro la tasa con el id: " + id);
        }
        return ResponseEntity.ok(tasa);
    }

    @PostMapping("/tasas")
    public Tasa agregar(@RequestBody Tasa tasa) {
        return tasaServicio.guardar(tasa);
    }

    @GetMapping("/tasas/{id}/{cantidad}")
    public ResponseEntity<Double> calcular(@PathVariable Integer id, @PathVariable Double cantidad) {
        Tasa tasaExistente = tasaServicio.buscarPorId(id);
        if (tasaExistente == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro la tasa con el id: " + id);
        }
        Double resultado = tasaExistente.getValor() * cantidad;
        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/tasas/{id}")
    public ResponseEntity<Tasa> actualizar(@PathVariable Integer id, @RequestBody Tasa tasaActualizada) {
        Tasa tasaExistente = tasaServicio.buscarPorId(id);
        if (tasaExistente == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro la tasa con el id: " + id);
        }
        tasaExistente.setValor(tasaActualizada.getValor());
        tasaServicio.guardar(tasaExistente);
        return ResponseEntity.ok(tasaExistente);
    }
}
