package com.cursomc.services;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.repositories.*;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepo;

	@Autowired
	private ProdutoRepository produtoRepo;

	@Autowired
	private ItemPedidoRepository itemRepo;
	
	public Pedido find(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: "+ Categoria.class.getName()));
	}


	public Pedido insert(Pedido obj){
		obj.setId(null);
		obj.setDate(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		setPedidoPagamento(obj);

		obj = repo.save(obj);
		pagamentoRepo.save(obj.getPagamento());
		setItensPedido(obj);
		itemRepo.saveAll(obj.getItens());


		return obj;
	}

	private void setItensPedido(Pedido obj) {
		obj.getItens().stream().forEach(itemPedido -> {
			itemPedido.setDesconto(0.0);
			itemPedido.setPreco(produtoRepo.findById(itemPedido.getProduto().getId()).get().getPreco());
			itemPedido.setPedido(obj);
		});
	}

	private void setPedidoPagamento(Pedido obj) {
		if(obj.getPagamento() instanceof PagamentoComBoleto){
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getDate());
		}
	}

}
