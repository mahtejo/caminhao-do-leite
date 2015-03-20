package models.dica;

import models.opiniao.Opiniao;
import models.Tema;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

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

    @ElementCollection
    @MapKeyColumn
    @Column
    @CollectionTable
    private Map<String, Opiniao> opinioesDosUsuarios;

    public Dica(){
        this.opinioesDosUsuarios = new HashMap<String, Opiniao>();
    }

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

    public Map<String, Opiniao> getOpinioesDosUsuarios() {
        return opinioesDosUsuarios;
    }

    public void setOpinioesDosUsuarios(Map<String, Opiniao> opinioesDosUsuarios) {
        this.opinioesDosUsuarios = opinioesDosUsuarios;
    }

    public void addOpiniao(String usuario, Opiniao opiniao){
        opinioesDosUsuarios.put(usuario, opiniao);
    }

    public int getNumeroConcordaram(){
        int soma = 0;
        for (Opiniao o: opinioesDosUsuarios.values()){
            if (o.isConcorda()){
                soma++;
            }
        }
        return soma;
    }

    public int getNumeroDiscordaram(){
        int soma = 0;
        for (Opiniao o: opinioesDosUsuarios.values()){
            if (!o.isConcorda()){
                soma++;
            }
        }
        return soma;
    }

    public double concordancia(){
        double concordaram = getNumeroConcordaram();
        double discordaram = getNumeroDiscordaram();
        return concordaram / (concordaram + discordaram);
    }
}
