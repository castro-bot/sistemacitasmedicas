package ec.webmarket.restful.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.webmarket.restful.domain.Pais;
import ec.webmarket.restful.domain.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

	List<Provincia> findByPais(Pais pais);
}