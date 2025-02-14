package ec.webmarket.restful.dto.v1;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CitaDTO {
    private Long id;
    private Long pacienteId;
    private Long dentistaId;
    private Long horarioId;
    private LocalDate fechaCita;
    private LocalTime horaCita;
    private String estadoCita;
    private String motivoConsulta;
}
