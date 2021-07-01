package com.cursomc.repositories;

import com.cursomc.domain.Cidade;
import com.cursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepository  extends JpaRepository<Estado, Integer> {
}
