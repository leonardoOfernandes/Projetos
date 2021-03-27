package com.cursomc.services;

import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.repositories.CidadeRepository;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EnderecoRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: "+ Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(ClienteNewDTO obj){
		Cliente cliente = fromDTO(obj);
		cliente.setId(null);
		cliente = repo.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
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

	public Cliente fromDTO(ClienteNewDTO objDto){
		Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cidade= new Cidade(objDto.getCidadeId(),null,null);
		Endereco endereco = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),objDto.getBairro(), objDto.getCep(),cliente, cidade);

		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDto.getTelefone1());

		if(objDto.getTelefone2() !=null){
			cliente.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() !=null){
			cliente.getTelefones().add(objDto.getTelefone3());
		}


		return cliente;
	}
}
