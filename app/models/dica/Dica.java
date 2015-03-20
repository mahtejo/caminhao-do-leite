package models.dica;

import models.Tema;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by orion on 19/03/15.
 */
@Entity(name="Dica")
public abstract class Dica {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Tema tema;

    public Dica(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract String toString();

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public abstract String getTipo();

}
