package ec.webmarket.restful.api.v1;

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
import org.springframework.web.bind.annotation.RestController;

import ec.webmarket.restful.common.ApiConstants;
import ec.webmarket.restful.dto.v1.DentistaDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.DentistaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = {ApiConstants.URI_API_V1_DENTISTA})
public class DentistaController {
    
    @Autowired
    private DentistaService entityService;
    
    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, entityService.findAll(new DentistaDTO())), 
            HttpStatus.OK
        );
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DentistaDTO dto) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, entityService.create(dto)), 
            HttpStatus.CREATED
        );
    }
    
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody DentistaDTO dto) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, entityService.update(dto)), 
            HttpStatus.OK
        );
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        DentistaDTO dto = new DentistaDTO();
        dto.setId(id);
        entityService.delete(dto);
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, "Dentista eliminado"), 
            HttpStatus.OK
        );
    }
    
    @GetMapping("/{cedula}")
    public ResponseEntity<?> getByCedula(@PathVariable String cedula) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, entityService.findByCedula(cedula)), 
            HttpStatus.OK
        );
    }
    
    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<?> getByEspecialidad(@PathVariable String especialidad) {
        return new ResponseEntity<>(
            new ApiResponseDTO<>(true, entityService.findByEspecialidad(especialidad)), 
            HttpStatus.OK
        );
    }
}
