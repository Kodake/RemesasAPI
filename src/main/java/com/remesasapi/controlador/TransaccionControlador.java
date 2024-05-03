package com.remesasapi.controlador;

import com.remesasapi.dto.GetTransaccionDTO;
import com.remesasapi.dto.TasaDTO;
import com.remesasapi.dto.TransaccionDTO;
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
        transaccionDTO.setClienteDestino(transaccion.getClienteOrigen().getIdCliente());

        return ResponseEntity.ok(transaccionDTO);
    }
}
