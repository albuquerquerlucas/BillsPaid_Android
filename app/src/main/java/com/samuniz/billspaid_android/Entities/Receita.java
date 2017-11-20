package com.samuniz.billspaid_android.Entities;

import java.io.Serializable;

public class Receita implements Serializable {
    private String id;
    private String descricao;
    private String valor;
    private String idCliente;

    public Receita (){
    }

    public Receita(String id, String descricao, String valor, String idCliente) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.idCliente = idCliente;
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

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
