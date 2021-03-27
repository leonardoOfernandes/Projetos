package com.cursomc.services;

import com.cursomc.domain.Cliente;
import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.ClienteRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: "+ Cliente.class.getName()));
	}
	public Cliente insert(ClienteDTO obj){
		obj.setId(null);
		return repo.save(fromDTO(obj));
	}

	public Cliente update(Integer id,ClienteDTO obj) throws ObjectNotFoundException {
		obj.setId(id);
		Cliente cliente = find(id);
		updateData(cliente, obj);
		return repo.save(cliente);
	}

	private void updateData(Cliente cliente, ClienteDTO obj) {
		cliente.setNome(obj.getNome());
		cliente.setEmail(obj.getEmail());

	}

	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException exception){
			throw new DataIntegrityViolationException("Não é possivel excluir um cliente porque há entidades relacionadas");
		}

	}

	public List<ClienteDTO> findAll(){
		List<Cliente> list = repo.findAll();
		List<ClienteDTO> listDto = list.stream().map(Cliente -> new ClienteDTO(Cliente)).collect(Collectors.toList());
		return listDto;
	}

	public Page<Cliente> findAllByPage(Integer page,
										 Integer linesPerPage,
										 String orderBy,
										 String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);

	}

	public Cliente fromDTO(ClienteDTO objDto){
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
}
