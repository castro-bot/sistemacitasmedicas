package ec.webmarket.restful.api.v1;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.webmarket.restful.common.ApiConstants;
import ec.webmarket.restful.dto.v1.HorarioDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.HorarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {ApiConstants.URI_API_V1_HORARIO})
public class HorarioController {
    
    @Autowired
    private HorarioService horarioService;
    
    // Creación de horarios según la disponibilidad del odontólogo
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody HorarioDTO dto) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, horarioService.create(dto)), 
            HttpStatus.CREATED
        );
    }
    
    // Actualización y bloqueo de horarios ya atendidos o no disponibles
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody HorarioDTO dto) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, horarioService.update(dto)), 
            HttpStatus.OK
        );
    }
    
    // Visualización de horarios disponibles y no disponibles
    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, horarioService.findAll(new HorarioDTO())), 
            HttpStatus.OK
        );
    }
    
    // Visualización de horarios de un odontólogo específico
    @GetMapping("/dentista/{dentistaId}")
    public ResponseEntity<?> getHorariosByDentista(@PathVariable Long dentistaId) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, horarioService.findHorariosDisponibles(dentistaId)), 
            HttpStatus.OK
        );
    }

    // Visualización de horarios de una fecha específica
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<?> getHorariosByFecha(@PathVariable LocalDate fecha) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, horarioService.findByFecha(fecha)), 
            HttpStatus.OK
        );
    }
}