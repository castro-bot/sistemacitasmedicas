package ec.webmarket.restful.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Dentista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(unique = true, nullable = false)
	private String cedula;

	@Column(length = 50, nullable = false)
	private String nombre;

	@Column(length = 50, nullable = false)
	private String apellido;

	@Column(nullable = false)
	private String especialidad;

	@Column(unique = true, nullable = false)
	private String telefono;

	@Column(unique = true, nullable = false)
	private String email;

	@JsonManagedReference
	@OneToMany(mappedBy = "dentista", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Horario> horarios;

	@JsonManagedReference
	@OneToMany(mappedBy = "dentista", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Cita> citas;

}
