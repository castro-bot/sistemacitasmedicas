package ec.webmarket.restful.service.crud;

import java.time.LocalDate;
import java.time.LocalTime;
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

	@Override
	public List<HorarioDTO> findAll(HorarioDTO dto) {
		List<Horario> horarios = repository.findAll();
		return horarios.stream().map(this::mapToDto) // ✅ Ensures correct mapping
				.collect(Collectors.toList());
	}

	public List<Horario> findByDentistaAndFechaBetween(Long dentistaId, LocalDate fechaInicio, LocalDate fechaFin) {
		Dentista dentista = dentistaRepository.findById(dentistaId)
				.orElseThrow(() -> new ApiException("Dentista no encontrado"));
		return repository.findByDentistaAndFechaBetween(dentista, fechaInicio, fechaFin);
	}

	public List<HorarioDTO> findHorariosByDentista(Long dentistaId) {
		Dentista dentista = dentistaRepository.findById(dentistaId)
				.orElseThrow(() -> new RuntimeException("Dentista no encontrado"));

		List<Horario> horarios = repository.findByDentista(dentista);

		if (horarios.isEmpty()) {
			throw new RuntimeException("No se encontraron horarios para el dentista con ID " + dentistaId);
		}

		return horarios.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public HorarioDTO create(HorarioDTO dto) {
		List<Horario> horariosExistentes = findByDentistaAndFechaBetween(dto.getDentista().getId(), dto.getFecha(),
				dto.getFecha());

		for (Horario horarioExistente : horariosExistentes) {
			if (existeSuperposicion(dto.getHoraInicio(), dto.getHoraFin(), horarioExistente.getHoraInicio(),
					horarioExistente.getHoraFin())) {
				throw new ApiException("El horario se superpone con otro existente");
			}
		}

		return super.create(dto);
	}

	public HorarioDTO update(HorarioDTO dto) {
		Horario horario = repository.findById(dto.getId())
				.orElseThrow(() -> new RuntimeException("Horario no encontrado"));

		// 🔥 Ensure Dentista is fully loaded before updating Horario
		Dentista dentista = dentistaRepository.findById(horario.getDentista().getId())
				.orElseThrow(() -> new RuntimeException("Dentista no encontrado"));

		horario.setDentista(dentista); // ✅ Prevents Hibernate from setting NULL values

		horario.setFecha(dto.getFecha());
		horario.setHoraInicio(dto.getHoraInicio());
		horario.setHoraFin(dto.getHoraFin());
		horario.setDisponible(dto.isDisponible());

		Horario updatedHorario = repository.save(horario);

		return mapToDto(updatedHorario);
	}

	private boolean existeSuperposicion(LocalTime inicio1, LocalTime fin1, LocalTime inicio2, LocalTime fin2) {
		return !fin1.isBefore(inicio2) && !inicio1.isAfter(fin2);
	}

	public List<HorarioDTO> findByFecha(LocalDate fecha) {
		List<Horario> horarios = repository.findByFecha(fecha);
		return horarios.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public Horario mapToDomain(HorarioDTO dto) {
		if (dto.getId() != null) {
			Optional<Horario> horarioExistente = repository.findById(dto.getId());
			if (horarioExistente.isPresent()) {
				Horario horario = horarioExistente.get();
				List<Cita> citas = horario.getCitas();

				// Ensure that Dentista is fully loaded
				if (dto.getDentista() != null && dto.getDentista().getId() != null) {
					Dentista dentista = dentistaRepository.findById(dto.getDentista().getId())
							.orElseThrow(() -> new RuntimeException("Dentista no encontrado"));
					horario.setDentista(dentista);
				} else {
					throw new RuntimeException("Dentista no puede ser nulo en la actualización de horario.");
				}

				modelMapper.map(dto, horario);
				horario.setCitas(citas);
				return horario;
			}
		}

		// If creating a new horario
		Horario newHorario = modelMapper.map(dto, Horario.class);
		if (dto.getDentista() != null && dto.getDentista().getId() != null) {
			Dentista dentista = dentistaRepository.findById(dto.getDentista().getId())
					.orElseThrow(() -> new RuntimeException("Dentista no encontrado"));
			newHorario.setDentista(dentista);
		} else {
			throw new RuntimeException("Dentista no puede ser nulo al crear un nuevo horario.");
		}
		return newHorario;
	}

	@Override
	public HorarioDTO mapToDto(Horario domain) {
		return modelMapper.map(domain, HorarioDTO.class);
	}
}
