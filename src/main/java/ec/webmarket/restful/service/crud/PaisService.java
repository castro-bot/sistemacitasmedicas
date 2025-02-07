package ec.webmarket.restful.service.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.webmarket.restful.domain.Pais;
import ec.webmarket.restful.dto.v1.PaisDTO;
import ec.webmarket.restful.persistence.PaisRepository;
import ec.webmarket.restful.service.GenericCrudServiceImpl;

@Service
public class PaisService extends GenericCrudServiceImpl<Pais, PaisDTO> {

	@Autowired
	private PaisRepository repository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public Optional<Pais> find(PaisDTO dto) {
		return repository.findById(dto.getId());
	}

	public List<Pais> findByNombre(String nombre) {
		return repository.findByNombre(nombre);
	}

	public List<Pais> findByFechaCreacion(LocalDate fechaCreacion) {
		return repository.findByFechaCreacion(fechaCreacion);
	}

	@Override
	public Pais mapToDomain(PaisDTO dto) {
		return modelMapper.map(dto, Pais.class);
	}

	@Override
	public PaisDTO mapToDto(Pais domain) {
		return modelMapper.map(domain, PaisDTO.class);
	}

}