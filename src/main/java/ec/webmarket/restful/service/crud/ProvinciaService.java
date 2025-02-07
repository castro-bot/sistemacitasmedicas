package ec.webmarket.restful.service.crud;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.webmarket.restful.domain.Pais;
import ec.webmarket.restful.domain.Provincia;
import ec.webmarket.restful.dto.v1.ProvinciaDTO;
import ec.webmarket.restful.persistence.ProvinciaRepository;
import ec.webmarket.restful.service.GenericCrudServiceImpl;

@Service
public class ProvinciaService extends GenericCrudServiceImpl<Provincia, ProvinciaDTO> {

	@Autowired
	private ProvinciaRepository repository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public Optional<Provincia> find(ProvinciaDTO dto) {
		return repository.findById(dto.getId());
	}

	public List<Provincia> findByPais(Long id) {
		Pais pais = new Pais();
		pais.setId(id);
		return repository.findByPais(pais);
	}

	@Override
	public Provincia mapToDomain(ProvinciaDTO dto) {
		return modelMapper.map(dto, Provincia.class);
	}

	@Override
	public ProvinciaDTO mapToDto(Provincia domain) {
		return modelMapper.map(domain, ProvinciaDTO.class);
	}

}