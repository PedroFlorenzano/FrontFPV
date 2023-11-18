package com.example.falefacens.models;

import java.util.List;

public class Categoria {
    private Long id;
    private String nome;
    private List<Long> contatosId;

    public Categoria() {
    }

    public Categoria(String nome, List<Long> contatosId) {
        this.nome = nome;
        this.contatosId = contatosId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Long> getContatosId() {
        return contatosId;
    }

    public void setContatosId(List<Long> contatosId) {
        this.contatosId = contatosId;
    }
}
