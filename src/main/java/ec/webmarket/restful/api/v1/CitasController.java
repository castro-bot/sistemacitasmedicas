package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.dto.v1.CitaDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/citas")
public class CitasController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<?> getAllCitas() {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, citaService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCitaById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, citaService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<?> createCita(@RequestBody CitaDTO citaDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, citaService.create(citaDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCita(@PathVariable Long id, @RequestBody CitaDTO citaDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, citaService.update(id, citaDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCita(@PathVariable Long id) {
        citaService.delete(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "Cita cancelada correctamente."));
    }

    @PostMapping("/{id}/recordatorio")
    public ResponseEntity<?> enviarRecordatorio(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, citaService.enviarRecordatorio(id)));
    }
}
