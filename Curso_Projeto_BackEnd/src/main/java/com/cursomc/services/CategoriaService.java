package com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.repositories.CategoriaRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: "+ Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj){
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Integer id,Categoria obj) throws ObjectNotFoundException {
		find(id);
		obj.setId(id);
		return repo.save(obj);
	}

}
