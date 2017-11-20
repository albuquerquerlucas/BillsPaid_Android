package com.samuniz.billspaid_android.Entities;

import java.util.List;

public class Cliente {

    private String id;
    private String nome;

    public Cliente() {
    }

    public Cliente(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
