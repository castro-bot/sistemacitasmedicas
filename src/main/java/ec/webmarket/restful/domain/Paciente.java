package ec.webmarket.restful.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;

@Getter
@Setter
@Entity
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String cedula;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 15, unique = true)
    private String telefono;

    @Column(nullable = false)
    private LocalDate fechaRegistro;
    
	@JsonManagedReference
    @OneToMany(mappedBy = "paciente", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Cita> citas;
}
