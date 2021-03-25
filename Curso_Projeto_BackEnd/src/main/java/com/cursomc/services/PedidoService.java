package com.cursomc.services;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Pedido;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.repositories.PedidoRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: "+ Categoria.class.getName()));
	}

}
