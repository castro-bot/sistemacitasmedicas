package ec.webmarket.restful.dto.v1;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
    private LocalDate fechaNacimiento;
    private String direccionResidencia;
}
