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
    private HorarioService entityService;
    
    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, entityService.findAll(new HorarioDTO())), 
            HttpStatus.OK
        );
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody HorarioDTO dto) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, entityService.create(dto)), 
            HttpStatus.CREATED
        );
    }
    
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody HorarioDTO dto) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, entityService.update(dto)), 
            HttpStatus.OK
        );
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        HorarioDTO dto = new HorarioDTO();
        dto.setId(id);
        entityService.delete(dto);
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, "Horario eliminado"), 
            HttpStatus.OK
        );
    }
    
    @GetMapping("/dentista/{dentistaId}/fecha")
    public ResponseEntity<?> getHorariosByDentistaAndFecha(
            @PathVariable Long dentistaId,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, 
                entityService.findByDentistaAndFechaBetween(dentistaId, fechaInicio, fechaFin)), 
            HttpStatus.OK
        );
    }
    
    @GetMapping("/dentista/{dentistaId}/disponibles")
    public ResponseEntity<?> getHorariosDisponibles(@PathVariable Long dentistaId) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, entityService.findHorariosDisponibles(dentistaId)), 
            HttpStatus.OK
        );
    }
}