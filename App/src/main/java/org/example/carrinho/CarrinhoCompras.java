package org.example.carrinho;

import org.example.model.CarrinhoProdutos;
import org.example.model.Produto;

import java.util.List;
import java.util.stream.Collectors;

public class CarrinhoCompras {

    public CarrinhoProdutos carrinho;

    public CarrinhoCompras(CarrinhoProdutos carrinho) {
        this.carrinho = carrinho;
    }

    public void adicionarProduto(Produto produto) {
        var produtoExistente = carrinho.getCarrinho().stream()
                .filter(p -> p.getNome().equals(produto.getNome()))
                .peek(p -> p.setQtd(p.getQtd() + produto.getQtd()))
                .findFirst();

        if (produtoExistente.isPresent())
            return;

        carrinho.getCarrinho().add(produto);
    }

    public void removerProduto(Produto produto) {
        var removido = carrinho.getCarrinho().remove(produto);

        if (!removido)
            throw new RuntimeException();
    }

    public double calcularTotalCarrinho() {
        if(carrinho.getCarrinho().isEmpty()) {
            throw new RuntimeException();
        }

        return carrinho.getCarrinho().stream()
                .mapToDouble(Produto::getValor)
                .sum();
    }

    public void alterarQuantidadeProduto(Produto produto, int qtd) {
        carrinho.getCarrinho().forEach(filteredProduct -> {
            if (filteredProduct.getId() == produto.getId())
                filteredProduct.setQtd(qtd);
        });
    }

    public List<Integer> buscarListaProdutos() {
        if(carrinho.getCarrinho().isEmpty()) {
            throw new RuntimeException();
        }
           return carrinho.getCarrinho().stream()
                   .map(Produto::getId)
                   .collect(Collectors.toList());
    }

}
