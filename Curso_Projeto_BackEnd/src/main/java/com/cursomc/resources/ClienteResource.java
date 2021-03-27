package com.cursomc.resources;

import com.cursomc.domain.Cliente;
import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.services.ClienteService;
import com.cursomc.services.ClienteService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() throws ObjectNotFoundException {
		List<ClienteDTO> list = service.findAll();
		return ResponseEntity.ok(list);
	}

	@RequestMapping(value = "/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findAllByPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
															@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
															@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
															@RequestParam(value = "direction", defaultValue = "ASC") String direction) throws ObjectNotFoundException {
		Page<Cliente> list = service.findAllByPage(page, linesPerPage, orderBy, direction);

		Page<ClienteDTO> listDto = list.map(Cliente -> new ClienteDTO(Cliente));

		return ResponseEntity.ok(listDto);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO obj) throws ObjectNotFoundException{
		Cliente response = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id,@Valid @RequestBody ClienteDTO obj) throws ObjectNotFoundException{
		Cliente response = service.update(id, obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws ObjectNotFoundException {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}


}
