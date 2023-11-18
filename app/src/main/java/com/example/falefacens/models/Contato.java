package com.example.falefacens.models;

public class Contato {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Long categoriaId;

    public Contato() {
    }

    public Contato(String nome, String email, String telefone, Long categoriaId) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.categoriaId = categoriaId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
