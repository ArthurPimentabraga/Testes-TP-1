package org.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Produto {
    private int id;
    private String nome;
    private int qtd;
    private String descricao;
    private double valor;
}
