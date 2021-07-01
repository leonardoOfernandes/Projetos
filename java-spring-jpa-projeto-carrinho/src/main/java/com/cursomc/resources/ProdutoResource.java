package com.cursomc.resources;

import com.cursomc.domain.Produto;
import com.cursomc.dto.ProdutoDTO;
import com.cursomc.dto.request.PageProdutoRequest;
import com.cursomc.services.ProdutoService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) throws ObjectNotFoundException{
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestBody PageProdutoRequest body, @RequestParam(value = "page", defaultValue = "0") Integer page,
													 @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
													 @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
													 @RequestParam(value = "direction", defaultValue = "ASC") String direction) throws ObjectNotFoundException {
		Page<Produto> list = service.search(body.getNome(),body.getCategorias(), page, linesPerPage, orderBy, direction);

		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));

		return ResponseEntity.ok(listDto);
	}





}
