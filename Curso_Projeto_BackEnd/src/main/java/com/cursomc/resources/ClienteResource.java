package com.cursomc.resources;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cliente;
import com.cursomc.services.CategoriaService;
import com.cursomc.services.ClienteService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) throws ObjectNotFoundException{
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

}
