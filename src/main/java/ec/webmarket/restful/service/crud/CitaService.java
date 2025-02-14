package ec.webmarket.restful.service.crud;

import ec.webmarket.restful.domain.Cita;
import ec.webmarket.restful.domain.Dentista;
import ec.webmarket.restful.domain.Horario;
import ec.webmarket.restful.domain.Paciente;
import ec.webmarket.restful.dto.v1.CitaDTO;
import ec.webmarket.restful.persistence.CitaRepository;
import ec.webmarket.restful.persistence.DentistaRepository;
import ec.webmarket.restful.persistence.HorarioRepository;
import ec.webmarket.restful.persistence.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CitaService {

	@Autowired
	private CitaRepository citaRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private DentistaRepository dentistaRepository;

	@Autowired
	private HorarioRepository horarioRepository;

	private ModelMapper modelMapper = new ModelMapper();

	// Obtener historial de citas por dentista o paciente
	public List<CitaDTO> findByDentista(Long dentistaId) {
		Dentista dentista = dentistaRepository.findById(dentistaId)
				.orElseThrow(() -> new RuntimeException("Dentista no encontrado"));
		return citaRepository.findByDentista(dentista).stream().map(cita -> modelMapper.map(cita, CitaDTO.class))
				.toList();
	}

	public List<CitaDTO> findByPaciente(Long pacienteId) {
		Paciente paciente = pacienteRepository.findById(pacienteId)
				.orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
		return citaRepository.findByPaciente(paciente).stream().map(cita -> modelMapper.map(cita, CitaDTO.class))
				.toList();
	}

	// Agendar una nueva cita
	@Transactional
	public CitaDTO create(CitaDTO dto) {
	    // Fetch the horario
	    Horario horario = horarioRepository.findById(dto.getHorarioId())
	        .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

	    // Check if the horario is available
	    if (!horario.isDisponible()) {
	        // 🔹 Instead of throwing an error, return a message
	        dto.setEstadoCita("El horario seleccionado no está disponible.");
	        return dto;
	    }

	    // Fetch patient and dentist
	    Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
	        .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

	    Dentista dentista = dentistaRepository.findById(dto.getDentistaId())
	        .orElseThrow(() -> new RuntimeException("Dentista no encontrado"));

	    // Create new Cita
	    Cita cita = new Cita();
	    cita.setPaciente(paciente);
	    cita.setDentista(dentista);
	    cita.setHorario(horario);
	    cita.setFechaCita(dto.getFechaCita());
	    cita.setHoraCita(dto.getHoraCita());
	    cita.setEstadoCita(dto.getEstadoCita());
	    cita.setMotivoConsulta(dto.getMotivoConsulta());

	    // Mark horario as unavailable
	    horario.setDisponible(false);
	    horarioRepository.save(horario);

	    // Save Cita in DB
	    Cita citaGuardada = citaRepository.save(cita);

	    return mapToDto(citaGuardada);
	}


	// Modificar o reprogramar una cita existente
	public CitaDTO update(Long id, CitaDTO citaDTO) {
		Cita cita = citaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));

		// Cambiar horario si es necesario
		if (!cita.getHorario().getId().equals(citaDTO.getHorarioId())) {
			Horario nuevoHorario = horarioRepository.findById(citaDTO.getHorarioId())
					.orElseThrow(() -> new RuntimeException("Horario no encontrado"));
			if (!nuevoHorario.isDisponible()) {
				throw new RuntimeException("El nuevo horario no está disponible.");
			}

			// Liberar el horario anterior
			cita.getHorario().setDisponible(true);
			horarioRepository.save(cita.getHorario());

			// Asignar el nuevo horario
			nuevoHorario.setDisponible(false);
			cita.setHorario(nuevoHorario);
			horarioRepository.save(nuevoHorario);
		}

		cita.setFechaCita(citaDTO.getFechaCita());
		cita.setHoraCita(citaDTO.getHoraCita());
		cita.setEstadoCita(citaDTO.getEstadoCita());
		cita.setMotivoConsulta(citaDTO.getMotivoConsulta());

		Cita citaActualizada = citaRepository.save(cita);
		return modelMapper.map(citaActualizada, CitaDTO.class);
	}

	// Cancelar una cita
	public void delete(Long id) {
		Cita cita = citaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));

		Horario horario = cita.getHorario();
		horario.setDisponible(true);
		horarioRepository.save(horario);

		cita.setEstadoCita("Cancelada");
		citaRepository.save(cita);
	}

	public Cita mapToDomain(CitaDTO dto) {
		Cita cita = new Cita();

		if (dto.getId() != null) {
			cita = citaRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
		}

		// Ensure the patient exists
		if (dto.getPacienteId() != null) {
			Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
					.orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
			cita.setPaciente(paciente);
		}

		// Ensure the dentist exists
		if (dto.getDentistaId() != null) {
			Dentista dentista = dentistaRepository.findById(dto.getDentistaId())
					.orElseThrow(() -> new RuntimeException("Dentista no encontrado"));
			cita.setDentista(dentista);
		}

		// Ensure the schedule (Horario) exists
		if (dto.getHorarioId() != null) {
			Horario horario = horarioRepository.findById(dto.getHorarioId())
					.orElseThrow(() -> new RuntimeException("Horario no encontrado"));
			cita.setHorario(horario);
		}

		cita.setFechaCita(dto.getFechaCita());
		cita.setHoraCita(dto.getHoraCita());
		cita.setEstadoCita(dto.getEstadoCita());
		cita.setMotivoConsulta(dto.getMotivoConsulta());

		return cita;
	}

	public CitaDTO mapToDto(Cita cita) {
		CitaDTO dto = new CitaDTO();

		dto.setId(cita.getId());

		if (cita.getPaciente() != null) {
			dto.setPacienteId(cita.getPaciente().getId());
		}

		if (cita.getDentista() != null) {
			dto.setDentistaId(cita.getDentista().getId());
		}

		if (cita.getHorario() != null) {
			dto.setHorarioId(cita.getHorario().getId());
		}

		dto.setFechaCita(cita.getFechaCita());
		dto.setHoraCita(cita.getHoraCita());
		dto.setEstadoCita(cita.getEstadoCita());
		dto.setMotivoConsulta(cita.getMotivoConsulta());

		return dto;
	}

}