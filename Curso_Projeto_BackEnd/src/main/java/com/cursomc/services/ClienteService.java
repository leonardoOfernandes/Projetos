package com.cursomc.services;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cliente;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.repositories.ClienteRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: "+ Cliente.class.getName()));
	}

}
