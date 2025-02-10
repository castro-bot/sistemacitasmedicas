package ec.webmarket.restful.dto.v1;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaDTO {
    private Long id;
    private Long pacienteId;
    private Long dentistaId;
    private Long horarioId;
    private String tipoConsulta;
    private boolean recordatorioEnviado;
}
