package ec.webmarket.restful.persistence;

import ec.webmarket.restful.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByFechaHoraBetween(LocalDateTime start, LocalDateTime end);
}
