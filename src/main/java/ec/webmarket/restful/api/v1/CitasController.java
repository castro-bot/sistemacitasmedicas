package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.common.ApiConstants;
import ec.webmarket.restful.dto.v1.CitaDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {ApiConstants.URI_API_V1_CITA})
public class CitasController {

    @Autowired
    private CitaService citaService;

    @PostMapping
    public ResponseEntity<?> createCita(@RequestBody CitaDTO citaDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, citaService.create(citaDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCita(@PathVariable Long id, @RequestBody CitaDTO citaDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, citaService.update(id, citaDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelCita(@PathVariable Long id) {
        citaService.delete(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "Cita cancelada correctamente."));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<?> getCitasByPaciente(@PathVariable Long pacienteId) {
        List<CitaDTO> citas = citaService.findByPaciente(pacienteId);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, citas));
    }

    @GetMapping("/dentista/{dentistaId}")
    public ResponseEntity<?> getCitasByDentista(@PathVariable Long dentistaId) {
        List<CitaDTO> citas = citaService.findByDentista(dentistaId);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, citas));
    }
}
