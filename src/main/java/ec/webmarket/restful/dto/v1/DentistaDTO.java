package ec.webmarket.restful.dto.v1;

import lombok.Data;

@Data
public class DentistaDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String especialidad;
    private String email;
    private String telefono;
}