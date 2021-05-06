package com.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

    /**
     * @param nome
     * @param categorias
     * @param pageRequest
     * @deprecated  Because we used Spring Data instead of make a query<br/>
     *              use {@link #findDistinctByNomeContainingAndCategoriasIn(String, List, Pageable)} instead like this:
     *
     *
     * <blockquote><pre>
     * new ProdutoRepository.findDistinctByNomeContainingAndCategoriasIn("example", List<Categoria> ,Pageable)
     * </pre></blockquote>
     *
     **/
    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
    @Transactional(readOnly = true)
    @Deprecated
    Page<Produto> search(@Param("nome") String nome,@Param("categorias") List<Categoria> categorias, Pageable pageRequest);

    // funciona da mesma forma que o de cima
    @Transactional(readOnly = true)
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);

}
