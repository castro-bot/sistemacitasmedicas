package ec.webmarket.restful.api.v1;

import ec.webmarket.restful.dto.v1.PacienteDTO;
import ec.webmarket.restful.service.crud.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {ApiConstants.URI_API_V1_PACIENTE})
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    public ResponseEntity<PacienteDTO> create(@RequestBody PacienteDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
