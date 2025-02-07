package ec.webmarket.restful.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.webmarket.restful.domain.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> {

	List<Pais> findByNombre(String nombre);

	List<Pais> findByFechaCreacion(LocalDate fechaCreacion);

}
