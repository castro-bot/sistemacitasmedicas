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

    // Obtener un paciente por ID
    public PacienteDTO findById(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        return modelMapper.map(paciente, PacienteDTO.class);
    }

    // Obtener un paciente por Cédula
    public PacienteDTO findByCedula(String cedula) {
        Paciente paciente = pacienteRepository.findByCedula(cedula)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        return modelMapper.map(paciente, PacienteDTO.class);
    }

    // Buscar pacientes por nombre
    public List<PacienteDTO> findByNombre(String nombre) {
        return pacienteRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteDTO.class))
                .toList();
    }

    // Registrar un nuevo paciente
    public PacienteDTO create(PacienteDTO pacienteDTO) {
        pacienteDTO.setFechaRegistro(LocalDate.now());
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


    // Eliminar un paciente
    public void delete(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        pacienteRepository.delete(paciente);
    }
}
