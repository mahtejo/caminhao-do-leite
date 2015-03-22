package models;

import models.util.BCrypt;

import javax.persistence.*;

@Entity(name="Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String nome;
    @Column
    private String senha;
    @Column
    private String usuario;

    public Usuario() {
    }

    public Usuario(String nome, String password, String usuario) {
        this.nome = nome;
        this.usuario = usuario;
        setSenha(password);
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

    private String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public String getUser() {
        return usuario;
    }

    public void setUser(String user) {
        this.usuario = user;
    }

    public boolean autentica(String user, String password) {
        if (user.equals(getUser()) && BCrypt.checkpw(password, getSenha())){
            return true;
        }
        return false;
    }
}