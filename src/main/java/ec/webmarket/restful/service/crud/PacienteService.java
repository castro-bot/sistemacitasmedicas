package ec.webmarket.restful.service.crud;

import ec.webmarket.restful.domain.Paciente;
import ec.webmarket.restful.dto.v1.PacienteDTO;
import ec.webmarket.restful.persistence.PacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    public PacienteDTO create(PacienteDTO dto) {
        Paciente paciente = modelMapper.map(dto, Paciente.class);
        return modelMapper.map(repository.save(paciente), PacienteDTO.class);
    }

    public List<PacienteDTO> findAll() {
        return repository.findAll().stream().map(p -> modelMapper.map(p, PacienteDTO.class)).toList();
    }
}
