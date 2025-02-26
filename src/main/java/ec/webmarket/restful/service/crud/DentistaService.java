package ec.webmarket.restful.service.crud;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.webmarket.restful.common.ApiException;
import ec.webmarket.restful.domain.Cita;
import ec.webmarket.restful.domain.Dentista;
import ec.webmarket.restful.domain.Horario;
import ec.webmarket.restful.dto.v1.CitaDTO;
import ec.webmarket.restful.dto.v1.DentistaDTO;
import ec.webmarket.restful.persistence.DentistaRepository;
import ec.webmarket.restful.service.GenericCrudServiceImpl;

@Service
public class DentistaService extends GenericCrudServiceImpl<Dentista, DentistaDTO> {
    
    @Autowired
    private DentistaRepository repository;
    
    private ModelMapper modelMapper = new ModelMapper();
    
    @Override
    public Optional<Dentista> find(DentistaDTO dto) {
        return repository.findById(dto.getId());
    }
    
    public List<CitaDTO> findCitasAsignadas(Long dentistaId) {
        Dentista dentista = repository.findById(dentistaId)
            .orElseThrow(() -> new ApiException("Dentista no encontrado"));
            
        return dentista.getCitas().stream()
            .map(cita -> modelMapper.map(cita, CitaDTO.class))
            .collect(Collectors.toList());
    }
    
    @Override
    public Dentista mapToDomain(DentistaDTO dto) {
        if (dto.getId() != null) {
            Optional<Dentista> dentistaExistente = repository.findById(dto.getId());
            if (dentistaExistente.isPresent()) {
                Dentista dentista = dentistaExistente.get();
                List<Horario> horarios = dentista.getHorarios();
                List<Cita> citas = dentista.getCitas();
                modelMapper.map(dto, dentista);
                dentista.setHorarios(horarios);
                dentista.setCitas(citas);
                return dentista;
            }
        }
        return modelMapper.map(dto, Dentista.class);
    }
    
    @Override
    public DentistaDTO mapToDto(Dentista domain) {
        return modelMapper.map(domain, DentistaDTO.class);
    }
}