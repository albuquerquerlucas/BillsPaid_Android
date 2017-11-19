package com.samuniz.billspaid_android.Entities;

import java.io.Serializable;

public class Despesa implements Serializable{
    private String id;
    private String descricao;
    private String valor;
    private String dataD;
    private String conta;
    private String categoria;
    private String pago;

    public Despesa (){
    }

    public Despesa(String id, String descricao, String valor) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    /*public Despesa(String id, String descricao, String valor, String dataD, String conta, String categoria, String pago) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataD = dataD;
        this.conta = conta;
        this.categoria = categoria;
        this.pago = pago;
    }*/

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

    public String getDataD() {
        return dataD;
    }

    public void setDataD(String dataD) {
        this.dataD = dataD;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }
}
