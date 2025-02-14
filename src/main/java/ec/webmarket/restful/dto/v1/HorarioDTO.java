package ec.webmarket.restful.dto.v1;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HorarioDTO {
    private Long id;
    @NotNull(message = "El dentista no puede ser nulo")
    private DentistaDTO dentista;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean disponible;
}