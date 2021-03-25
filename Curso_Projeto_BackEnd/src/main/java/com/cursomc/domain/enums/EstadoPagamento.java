package com.cursomc.domain.enums;

import java.util.Arrays;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int cod;
    private String descricao;

    private EstadoPagamento(int cod, String descricao) {
        this.setCod(cod);
        this.setDescricao(descricao);
    }


    public int getCod() {
        return cod;
    }

    public void setCod(int cod)
    {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static EstadoPagamento toEnum(Integer cod){

        return cod == null ? null :
                Arrays.stream(EstadoPagamento.values())
                        .filter(tipoPagamento -> cod.equals(tipoPagamento.getCod()))
                        .findAny()
                        .orElseThrow(() -> new IllegalArgumentException("Id Invalido: " + cod));

    }
}
