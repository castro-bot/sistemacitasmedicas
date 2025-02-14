package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.common.ApiConstants;
import ec.webmarket.restful.dto.v1.PacienteDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {ApiConstants.URI_API_V1_PACIENTE})
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<?> createPaciente(@RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.create(pacienteDTO)));
    }

    @PutMapping
    public ResponseEntity<?> updatePaciente(@RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(true, pacienteService.update(pacienteDTO)));
    }
}
