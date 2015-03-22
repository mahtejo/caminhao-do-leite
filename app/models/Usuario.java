package models;

import models.util.BCrypt;

import javax.persistence.*;

@Entity(name="Usuario")
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String nome;
    @Column
    private String senha;
    @Column
    private String user;

    public Usuario() {
    }

    public Usuario(String nome, String password, String user) {
        setNome(nome);
        setUser(user);
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
        /*this.senha = BCrypt.hashpw(senha, BCrypt.gensalt());*/
        this.senha = senha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean autentica(String user, String password) {
        if (user.equals(getUser()) && password.equals(getSenha())/*BCrypt.checkpw(password, getSenha())*/){
            return true;
        }
        return false;
    }
}