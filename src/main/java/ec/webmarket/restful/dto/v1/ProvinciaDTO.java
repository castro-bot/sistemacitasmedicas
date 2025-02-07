package ec.webmarket.restful.dto.v1;

import lombok.Data;

@Data
public class ProvinciaDTO {

	private Long id;
	private String nombre;
	private PaisDTO pais;

}
