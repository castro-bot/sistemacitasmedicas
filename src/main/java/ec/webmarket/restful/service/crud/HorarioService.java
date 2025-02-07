package ec.webmarket.restful.service.crud;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.webmarket.restful.common.ApiException;
import ec.webmarket.restful.domain.Cita;
import ec.webmarket.restful.domain.Dentista;
import ec.webmarket.restful.domain.Horario;
import ec.webmarket.restful.dto.v1.HorarioDTO;
import ec.webmarket.restful.persistence.DentistaRepository;
import ec.webmarket.restful.persistence.HorarioRepository;
import ec.webmarket.restful.service.GenericCrudServiceImpl;

@Service
public class HorarioService extends GenericCrudServiceImpl<Horario, HorarioDTO> {
    
    @Autowired
    private HorarioRepository repository;
    
    @Autowired
    private DentistaRepository dentistaRepository;
    
    private ModelMapper modelMapper = new ModelMapper();
    
    @Override
    public Optional<Horario> find(HorarioDTO dto) {
        return repository.findById(dto.getId());
    }
    
    public List<Horario> findByDentistaAndFechaBetween(Long dentistaId, LocalDate fechaInicio, LocalDate fechaFin) {
        Dentista dentista = dentistaRepository.findById(dentistaId)
            .orElseThrow(() -> new ApiException("Dentista no encontrado"));
        return repository.findByDentistaAndFechaBetween(dentista, fechaInicio, fechaFin);
    }
    
    public List<Horario> findHorariosDisponibles(Long dentistaId) {
        Dentista dentista = dentistaRepository.findById(dentistaId)
            .orElseThrow(() -> new ApiException("Dentista no encontrado"));
        return repository.findByDentistaAndDisponible(dentista, true);
    }
    
    @Override
    public HorarioDTO create(HorarioDTO dto) {
        List<Horario> horariosExistentes = findByDentistaAndFechaBetween(
            dto.getDentista().getId(), 
            dto.getFecha(), 
            dto.getFecha()
        );
        
        for (Horario horarioExistente : horariosExistentes) {
            if (existeSuperposicion(dto.getHoraInicio(), dto.getHoraFin(), 
                                  horarioExistente.getHoraInicio(), 
                                  horarioExistente.getHoraFin())) {
                throw new ApiException("El horario se superpone con otro existente");
            }
        }
        
        return super.create(dto);
    }
    
    private boolean existeSuperposicion(LocalTime inicio1, LocalTime fin1, 
                                      LocalTime inicio2, LocalTime fin2) {
        return !fin1.isBefore(inicio2) && !inicio1.isAfter(fin2);
    }
    
    @Override
    public Horario mapToDomain(HorarioDTO dto) {
        if (dto.getId() != null) {
            Optional<Horario> horarioExistente = repository.findById(dto.getId());
            if (horarioExistente.isPresent()) {
                Horario horario = horarioExistente.get();
                List<Cita> citas = horario.getCitas();
                modelMapper.map(dto, horario);
                horario.setCitas(citas);
                return horario;
            }
        }
        return modelMapper.map(dto, Horario.class);
    }
    
    @Override
    public HorarioDTO mapToDto(Horario domain) {
        return modelMapper.map(domain, HorarioDTO.class);
    }
}

