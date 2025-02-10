package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.common.ApiConstants;
import ec.webmarket.restful.dto.v1.PacienteDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {ApiConstants.URI_API_V1_PACIENTE})
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<?> getAllPacientes() {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPacienteById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.findById(id)));
    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<?> getPacienteByCedula(@PathVariable String cedula) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.findByCedula(cedula)));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getPacientesByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.findByNombre(nombre)));
    }

    @PostMapping
    public ResponseEntity<?> createPaciente(@RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.create(pacienteDTO)));
    }

    @PutMapping
    public ResponseEntity<?> updatePaciente(@RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.update(pacienteDTO)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaciente(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(true, "Paciente eliminado correctamente."));
    }
}
