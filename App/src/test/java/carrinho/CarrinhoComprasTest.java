package carrinho;

import org.example.carrinho.CarrinhoCompras;
import org.example.model.CarrinhoProdutos;
import org.example.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarrinhoComprasTest {

    private CarrinhoCompras carrinhoCompras;

    private final Produto produtoTeste  = Produto.builder()
            .id(1).nome("Biscoito Oreo")
            .descricao("Biscoito Oreo Chocolate Recheado Sabor Baunilha 90g")
            .qtd(1).valor(4.49)
            .build();
    private final Produto produtoTeste2  = Produto.builder()
            .id(2).nome("Biscoito Trakinas")
            .descricao("Biscoito recheado trakinas morango 126g lacta")
            .qtd(1).valor(2.49)
            .build();

    @BeforeEach
    public void init() {
        CarrinhoProdutos carrinhoProdutos = CarrinhoProdutos.builder()
                .carrinho(new ArrayList<>())
                .build();
        carrinhoCompras = new CarrinhoCompras(carrinhoProdutos);
    }

    @Test
    @DisplayName("Verificar se o carrinho inicia sem nenhum produto")
    public void test01() {
        assertTrue(carrinhoCompras.carrinho.getCarrinho().isEmpty());
    }

    @Test
    @DisplayName("Ao tentar remover um produto de um carrinho vazio, deve propagar uma exception")
    public void test02() {
        assertThrows(RuntimeException.class, () -> carrinhoCompras.removerProduto(produtoTeste));
    }

    @Test
    @DisplayName("Ao adicionar um produto, verificar se o mesmo está no carrinho")
    public void test03() {
        carrinhoCompras.adicionarProduto(produtoTeste);

        var output = carrinhoCompras.carrinho.getCarrinho().stream().filter(
                produtoFiltrado -> produtoFiltrado.getId() == produtoTeste.getId()
        ).findFirst();

        assertTrue(output.isPresent());
        assertEquals(produtoTeste, output.get());
    }

    @Test
    @DisplayName("Ao remover um produto do carrinho, verificar se o saiu do carrinho")
    public void test06() {
        carrinhoCompras.adicionarProduto(produtoTeste);
        carrinhoCompras.removerProduto(produtoTeste);

        var output = carrinhoCompras.carrinho.getCarrinho().stream().filter(
                produtoFiltrado -> produtoFiltrado.getId() == produtoTeste.getId()
        )
        .findFirst();

        assertFalse(output.isPresent());
    }

    @Test
    @DisplayName("Ao solicitar o total de um carrinho vazio, deve propagar uma exception")
    public void test07() {
        assertThrows(RuntimeException.class, () -> carrinhoCompras.calcularTotalCarrinho());
    }

    @Test
    @DisplayName("Ao solicitar a lista de produtos de um carrinho vazio, deve propagar uma exception")
    public void test08() {
        assertThrows(RuntimeException.class, () -> carrinhoCompras.buscarListaProdutos());
    }

    @Test
    @DisplayName("Ao solicitar a lista de produtos de um carrinho, deve verificar se todos os produtos foram listados")
    public void test09() {
        carrinhoCompras.adicionarProduto(produtoTeste);
        carrinhoCompras.adicionarProduto(produtoTeste2);

        ArrayList<Integer> esperado = new ArrayList<>(List.of(1, 2));

        assertEquals(esperado, carrinhoCompras.buscarListaProdutos());
    }

    @Test
    @DisplayName("Ao solicitar o total de um carrinho com 2 produtos, deve verificar se o preço dos 2 produtos foram somados")
    public void test10() {
        carrinhoCompras.adicionarProduto(produtoTeste);
        carrinhoCompras.adicionarProduto(produtoTeste2);

        var soma = assertDoesNotThrow(
                () -> carrinhoCompras.calcularTotalCarrinho()
        );

        assertNotNull(soma);
        assertEquals((Double) 6.98, soma);
    }


    @Test
    @DisplayName("Ao trocar a quantidade de um produto existe, verificar se a quantidade foi trocada corretamente")
    public void test11() {
        carrinhoCompras.adicionarProduto(produtoTeste);
        carrinhoCompras.alterarQuantidadeProduto(produtoTeste, 6);

        var qtdEsperada = carrinhoCompras.carrinho.getCarrinho().get(0).getQtd();

        assertEquals(6, qtdEsperada);
    }

    @Test
    @DisplayName("Ao adicionar um produto com um nome já existente no carrinho, caso exista, deve adionar a quantidade do produto adicionado ao produto já existente no carrinho")
    public void test12() {
        carrinhoCompras.adicionarProduto(produtoTeste);
        carrinhoCompras.adicionarProduto(produtoTeste);

        var qtdEsperada = carrinhoCompras.carrinho.getCarrinho().get(0).getQtd();

        assertEquals(2, qtdEsperada);
    }

}
