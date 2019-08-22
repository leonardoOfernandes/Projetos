package com.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar(){
		
		Categoria c1 = new Categoria(1, "Informática" );
		Categoria c2 = new Categoria(2, "Escritório" );
		
		List<Categoria> listaCategoria = new ArrayList<>();
		listaCategoria.add(c1);
		listaCategoria.add(c2);
		
		return listaCategoria;
	}

}
