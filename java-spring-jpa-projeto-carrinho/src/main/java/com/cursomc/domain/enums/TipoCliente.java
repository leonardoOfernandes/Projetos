package com.cursomc.domain.enums;

import java.util.Arrays;

public enum TipoCliente {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Juridica");

    private int cod;
    private String descricao;

    private TipoCliente(int cod, String descricao) {
        this.setCod(cod);
        this.setDescricao(descricao);
    }


    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static TipoCliente toEnum(Integer cod){

        return cod == null ? null :
                Arrays.stream(TipoCliente.values())
                .filter(tipoCliente -> cod.equals(tipoCliente.getCod()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Id Invalido: " + cod));

    }
}
