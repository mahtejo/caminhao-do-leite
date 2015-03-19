package models;

import models.dica.Dica;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orion on 18/03/15.
 */

@Entity(name="Tema")
public class Tema {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String nome;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<Dica> dicas;

    public Tema(){
        dicas = new ArrayList<Dica>();
    }

    public Tema(String nome){
        dicas = new ArrayList<Dica>();
        setNome(nome);
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

    public void addDica(Dica dica) {
        dicas.add(dica);
    }

    public List<Dica> getDicas() {
        return dicas;
    }
}
