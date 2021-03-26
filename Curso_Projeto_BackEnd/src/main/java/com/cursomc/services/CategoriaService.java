package com.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cursomc.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		try {

			repo.deleteById(id);
		}catch (DataIntegrityViolationException exception){
			throw new DataIntegrityViolationException("Não é possivel excluir uma categoria que possui um produto");
		}

	}

	public List<CategoriaDTO> findAll(){
		List<Categoria> list = repo.findAll();
		List<CategoriaDTO> listDto = list.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
		return listDto;
	}

	public Page<Categoria> findAllByPage(Integer page,
										 Integer linesPerPage,
										 String orderBy,
										 String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);

	}

}
