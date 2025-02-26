package ec.webmarket.restful.persistence;

import ec.webmarket.restful.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
}
