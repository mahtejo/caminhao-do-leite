package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    public Tema(){
    }

    public Tema(String nome){
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
}
