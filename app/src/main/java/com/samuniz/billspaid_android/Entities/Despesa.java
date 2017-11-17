package com.samuniz.billspaid_android.Entities;

import java.io.Serializable;

public class Despesa implements Serializable{
    private String id;
    private String descricao;
    private String valor;

    public Despesa (){
    }

    public Despesa(String id, String descricao, String valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
