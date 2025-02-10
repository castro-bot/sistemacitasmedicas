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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // Obtener todas las citas
    public List<CitaDTO> findAll() {
        return citaRepository.findAll()
                .stream()
                .map(cita -> modelMapper.map(cita, CitaDTO.class))
                .toList();
    }

    // Obtener una cita por ID
    public CitaDTO findById(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        return modelMapper.map(cita, CitaDTO.class);
    }

    // Agendar una nueva cita
    public CitaDTO create(CitaDTO citaDTO) {
        Cita cita = modelMapper.map(citaDTO, Cita.class);

        cita.setPaciente(pacienteRepository.findById(citaDTO.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado")));

        cita.setDentista(dentistaRepository.findById(citaDTO.getDentistaId())
                .orElseThrow(() -> new RuntimeException("Dentista no encontrado")));

        Horario horario = horarioRepository.findById(citaDTO.getHorarioId())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        if (!horario.isDisponible()) {
            throw new RuntimeException("Horario no disponible");
        }

        horario.setDisponible(false);
        horarioRepository.save(horario);
        cita.setHorario(horario);
        cita.setRecordatorioEnviado(false);
        Cita citaGuardada = citaRepository.save(cita);
        return modelMapper.map(citaGuardada, CitaDTO.class);
    }

    // Modificar una cita
    public CitaDTO update(Long id, CitaDTO citaDTO) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

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

        cita.setTipoConsulta(citaDTO.getTipoConsulta());
        Cita citaActualizada = citaRepository.save(cita);
        return modelMapper.map(citaActualizada, CitaDTO.class);
    }

    // Cancelar una cita
    public void delete(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        Horario horario = cita.getHorario();
        horario.setDisponible(true);
        horarioRepository.save(horario);

        citaRepository.delete(cita);
    }

    // Enviar recordatorio de cita (simulado)
    public String enviarRecordatorio(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (cita.isRecordatorioEnviado()) {
            return "Recordatorio ya enviado previamente.";
        }

        // Simulación del envío de correo (integrar con servicio real)
        System.out.println("Enviando recordatorio de cita a " + cita.getPaciente().getEmail());

        cita.setRecordatorioEnviado(true);
        citaRepository.save(cita);

        return "Recordatorio enviado correctamente.";
    }
}
