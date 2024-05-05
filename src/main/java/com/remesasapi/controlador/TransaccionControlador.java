package com.remesasapi.controlador;

import com.remesasapi.dto.GetTransaccionDTO;
import com.remesasapi.dto.TasaDTO;
import com.remesasapi.dto.TransaccionDTO;
import com.remesasapi.excepcion.RecursoNoEncontradoExcepcion;
import com.remesasapi.modelo.Cliente;
import com.remesasapi.modelo.Moneda;
import com.remesasapi.modelo.Tasa;
import com.remesasapi.modelo.Transaccion;
import com.remesasapi.servicio.ITransaccionServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/remesas")
@CrossOrigin(value = "http://localhost:5173")
public class TransaccionControlador {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ITransaccionServicio transaccionServicio;

    @GetMapping("/transacciones")
    public Page<GetTransaccionDTO> listarPaginado(@RequestParam(defaultValue = "0") int pageNumber,
                                                  @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Transaccion> transaccionesPage = transaccionServicio.listarPaginado(pageable);

        Page<GetTransaccionDTO> transaccionDTOPage = transaccionesPage.map(transaccion -> {
            GetTransaccionDTO getTransaccionDTO = modelMapper.map(transaccion, GetTransaccionDTO.class);
            getTransaccionDTO.setMoneda(transaccion.getMoneda().getCodigo());
            getTransaccionDTO.setClienteDestino(transaccion.getClienteDestino().getDocumento() + " - "
                    + transaccion.getClienteDestino().getNombre() + " "
                    + transaccion.getClienteDestino().getApellido());
            return getTransaccionDTO;
        });

        return transaccionDTOPage;
    }

    @GetMapping("/transacciones/{codigo}")
    public ResponseEntity<?> buscarPorCodigo(@PathVariable String codigo) {
        Transaccion transaccion = transaccionServicio.buscarPorCodigo(codigo);
        if (transaccion == null) {
            String mensajeDeError = "No se encontro el envío con el código: " + codigo;
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }
        if (transaccion.isRetirado()) {
            String mensajeDeError = "El código: " + codigo + " ya fue retirado";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }

        TransaccionDTO transaccionDTO = modelMapper.map(transaccion, TransaccionDTO.class);
        transaccionDTO.setMoneda(transaccion.getMoneda().getIdMoneda());
        transaccionDTO.setClienteOrigen(transaccion.getClienteOrigen().getIdCliente());
        transaccionDTO.setClienteDestino(transaccion.getClienteDestino().getIdCliente());

        return ResponseEntity.ok(transaccionDTO);
    }

    @PostMapping("/transacciones")
    public ResponseEntity<?> agregar(@RequestBody TransaccionDTO transaccionDTO) {
        Transaccion transaccion = mapToTransaccion(transaccionDTO);
        transaccion.setCodigo(generateUuid());
        transaccion.setFecha(generateDate());
        transaccion.setRetirado(false);
        Transaccion transaccionGuardada = transaccionServicio.guardar(transaccion);
        TransaccionDTO respuesta = mapToTransaccionDTO(transaccionGuardada);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/transacciones/{codigo}")
    public ResponseEntity<?> actualizar(@PathVariable String codigo) {
        Transaccion transaccionExistente = transaccionServicio.buscarPorCodigo(codigo);
        if (transaccionExistente == null) {
            String mensajeDeError = "No se encontro la transacción con el código: " + codigo;
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }
        transaccionExistente.setRetirado(true);
        Transaccion transaccionGuardada = transaccionServicio.guardar(transaccionExistente);
        TransaccionDTO respuesta = mapToTransaccionDTO(transaccionGuardada);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/transacciones/{codigo}/intercambiar")
    public ResponseEntity<?> intercambiar(@PathVariable String codigo, @RequestBody TransaccionDTO transaccionActualizadaDTO) {
        Transaccion transaccionExistente = transaccionServicio.buscarPorCodigo(codigo);
        if (transaccionExistente == null) {
            String mensajeDeError = "No se encontro la transacción con el código: " + codigo;
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensajeDeError);
        }
        transaccionExistente.setMoneda(crearMoneda(transaccionActualizadaDTO.getMoneda()));
        transaccionExistente.setCantidad(transaccionActualizadaDTO.getCantidad());
        Transaccion transaccionGuardada = transaccionServicio.guardar(transaccionExistente);
        TransaccionDTO respuesta = mapToTransaccionDTO(transaccionGuardada);
        return ResponseEntity.ok(respuesta);
    }

    private Transaccion mapToTransaccion(TransaccionDTO transaccionDTO) {
        Transaccion transaccion = modelMapper.map(transaccionDTO, Transaccion.class);
        transaccion.setMoneda(crearMoneda(transaccionDTO.getMoneda()));
        transaccion.setClienteOrigen(crearCliente(transaccionDTO.getClienteOrigen()));
        transaccion.setClienteDestino(crearCliente(transaccionDTO.getClienteDestino()));
        return transaccion;
    }

    private TransaccionDTO mapToTransaccionDTO(Transaccion transaccion) {
        return modelMapper.map(transaccion, TransaccionDTO.class);
    }

    private String generateUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private LocalDate generateDate() {
        LocalDate date = LocalDate.now();
        return date;
    }

    private Cliente crearCliente(Integer idCliente) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        return cliente;
    }

    private Moneda crearMoneda(Integer idMoneda) {
        Moneda moneda = new Moneda();
        moneda.setIdMoneda(idMoneda);
        return moneda;
    }
}
