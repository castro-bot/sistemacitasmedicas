package ec.webmarket.restful.persistence;

import ec.webmarket.restful.domain.Cita;
import ec.webmarket.restful.domain.Dentista;
import ec.webmarket.restful.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPaciente(Paciente paciente);
    List<Cita> findByDentista(Dentista dentista);
}
