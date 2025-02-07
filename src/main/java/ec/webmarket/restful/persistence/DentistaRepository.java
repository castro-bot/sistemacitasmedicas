package ec.webmarket.restful.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.webmarket.restful.domain.Dentista;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
    Optional<Dentista> findByCedula(String cedula);
    List<Dentista> findByEspecialidad(String especialidad);
}
