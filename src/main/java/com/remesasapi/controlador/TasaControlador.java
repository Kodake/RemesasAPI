package com.remesasapi.controlador;

import com.remesasapi.dto.GetTasaDTO;
import com.remesasapi.dto.TasaDTO;
import com.remesasapi.excepcion.RecursoNoEncontradoExcepcion;
import com.remesasapi.modelo.Moneda;
import com.remesasapi.modelo.Tasa;
import com.remesasapi.servicio.ITasaServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/remesas")
@CrossOrigin(value = "http://localhost:5173")
public class TasaControlador {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ITasaServicio tasaServicio;

    @GetMapping("/tasas")
    public Page<GetTasaDTO> listarPaginado(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Tasa> tasasPage = tasaServicio.listarPaginado(pageable);

        Page<GetTasaDTO> tasaDTOPage = tasasPage.map(tasa -> {
            GetTasaDTO getTasaDTO = modelMapper.map(tasa, GetTasaDTO.class);
            getTasaDTO.setMonedaOrigen(tasa.getMonedaOrigen().getNombre() + " - " + tasa.getMonedaOrigen().getCodigo());
            getTasaDTO.setMonedaDestino(tasa.getMonedaDestino().getNombre() + " - " + tasa.getMonedaDestino().getCodigo());
            return getTasaDTO;
        });

        return tasaDTOPage;
    }

    @GetMapping("/tasas/listar")
    public List<Tasa> listar() {
        return tasaServicio.listar();
    }

    @GetMapping("/tasas/{id}")
    public ResponseEntity<TasaDTO> buscarPorId(@PathVariable Integer id) {
        Tasa tasa = tasaServicio.buscarPorId(id);
        if (tasa == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró la tasa con el id: " + id);
        }

        TasaDTO tasaDTO = modelMapper.map(tasa, TasaDTO.class);
        tasaDTO.setMonedaOrigen(tasa.getMonedaOrigen().getIdMoneda());
        tasaDTO.setMonedaDestino(tasa.getMonedaDestino().getIdMoneda());

        return ResponseEntity.ok(tasaDTO);
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

    @PostMapping("/tasas")
    public ResponseEntity<?> agregar(@RequestBody TasaDTO tasaDTO) {
        Tasa tasa = mapToTasa(tasaDTO);
        Tasa tasaGuardada = tasaServicio.guardar(tasa);
        TasaDTO respuesta = mapToTasaDTO(tasaGuardada);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/tasas/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody TasaDTO tasaActualizadaDTO) {
        Tasa tasaExistente = tasaServicio.buscarPorId(id);
        if (tasaExistente == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro la tasa con el id: " + id);
        }
        tasaExistente.setValor(tasaActualizadaDTO.getValor());
        tasaExistente.setMonedaOrigen(crearMoneda(tasaActualizadaDTO.getMonedaOrigen()));
        tasaExistente.setMonedaDestino(crearMoneda(tasaActualizadaDTO.getMonedaDestino()));
        Tasa tasaGuardada = tasaServicio.guardar(tasaExistente);
        TasaDTO respuesta = mapToTasaDTO(tasaGuardada);
        return ResponseEntity.ok(respuesta);
    }

    private Tasa mapToTasa(TasaDTO tasaDTO) {
        Tasa tasa = modelMapper.map(tasaDTO, Tasa.class);
        tasa.setMonedaOrigen(crearMoneda(tasaDTO.getMonedaOrigen()));
        tasa.setMonedaDestino(crearMoneda(tasaDTO.getMonedaDestino()));
        return tasa;
    }

    private TasaDTO mapToTasaDTO(Tasa tasa) {
        return modelMapper.map(tasa, TasaDTO.class);
    }

    private Moneda crearMoneda(Integer idMoneda) {
        Moneda moneda = new Moneda();
        moneda.setIdMoneda(idMoneda);
        return moneda;
    }
}
