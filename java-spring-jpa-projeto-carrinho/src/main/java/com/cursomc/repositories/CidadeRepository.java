package com.cursomc.repositories;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
}

