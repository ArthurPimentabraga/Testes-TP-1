package org.example.model;

import lombok.Builder;
import lombok.Data;
import org.example.model.Produto;

import java.util.ArrayList;

@Data
@Builder
public class CarrinhoProdutos {
    private ArrayList<Produto> carrinho;
}
