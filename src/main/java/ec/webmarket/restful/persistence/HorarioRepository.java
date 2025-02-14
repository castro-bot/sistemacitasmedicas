package ec.webmarket.restful.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.webmarket.restful.domain.Dentista;
import ec.webmarket.restful.domain.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByDentistaAndFechaBetween(Dentista dentista, LocalDate fechaInicio, LocalDate fechaFin);
    List<Horario> findByDentistaAndDisponible(Dentista dentista, boolean disponible);
    List<Horario> findByFecha(LocalDate fecha);
}
