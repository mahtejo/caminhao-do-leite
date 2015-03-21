package models.dica;

import models.Tema;

import javax.persistence.*;


/**
 * Created by orion on 19/03/15.
 */
@Entity(name="Dica")
public abstract class Dica extends DicaGenerica {

    @ManyToOne
    private Tema tema;

    public Dica() {
    }

    public Dica(String usuario, Tema tema) {
        super(usuario);
        setTema(tema);
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
