package ec.webmarket.restful.service.crud;

import ec.webmarket.restful.domain.Paciente;
import ec.webmarket.restful.dto.v1.PacienteDTO;
import ec.webmarket.restful.persistence.PacienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    private ModelMapper modelMapper = new ModelMapper();

    // Obtener todos los pacientes
    public List<PacienteDTO> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteDTO.class))
                .toList();
    }

    // Registrar un nuevo paciente
    public PacienteDTO create(PacienteDTO pacienteDTO) {
        pacienteDTO.setFechaRegistro(LocalDate.now());
        if (pacienteDTO.getFechaNacimiento() == null) {
            throw new RuntimeException("La fecha de nacimiento es obligatoria.");
        }
        Paciente paciente = modelMapper.map(pacienteDTO, Paciente.class);
        return modelMapper.map(pacienteRepository.save(paciente), PacienteDTO.class);
    }


    // Actualizar un paciente
    public PacienteDTO update(PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteRepository.findById(pacienteDTO.getId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setApellido(pacienteDTO.getApellido());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setTelefono(pacienteDTO.getTelefono());

        return modelMapper.map(pacienteRepository.save(paciente), PacienteDTO.class);
    }

}
