package models.dica;

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

    @Column
    private String usuario;

    @ElementCollection
    @MapKeyColumn
    @Column
    @CollectionTable
    private Map<String, String> opinioesNegativas;

    @ElementCollection
    @MapKeyColumn
    @Column
    @CollectionTable
    private Map<String, String> opinioesPositivas;

    public Dica(){
        this.opinioesNegativas = new HashMap<String, String>();
        this.opinioesPositivas = new HashMap<String, String>();
    }

    public Dica(String usuario){
        this.opinioesNegativas = new HashMap<String, String>();
        this.opinioesPositivas = new HashMap<String, String>();
        setUsuario(usuario);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract String toString();

    public void addOpiniaoPositiva(String usuario){
        opinioesNegativas.remove(usuario);
        opinioesPositivas.put(usuario, "");
    }

    public void addOpiniaoNegativa(String usuario, String opiniao) throws Exception {
        if (opiniao.length() > 100){
            throw new Exception("Erro: Opinião deve conter menos que 100 caracteres!");
        } else if (opiniao.length() == 0){
            throw new Exception("Erro: Opinião deve conter mais que 0 caracteres!");
        }
        opinioesPositivas.remove(usuario);
        opinioesNegativas.put(usuario, opiniao);
    }

    public int getNumeroConcordaram(){
        return opinioesPositivas.size();
    }

    public int getNumeroDiscordaram(){
        return opinioesNegativas.size();
    }

    public double concordancia(){
        double concordaram = getNumeroConcordaram();
        double discordaram = getNumeroDiscordaram();
        return concordaram / (concordaram + discordaram);
    }

    public Map<String, String> getOpinioesNegativas() {
        return opinioesNegativas;
    }

    public void setOpinioesNegativas(Map<String, String> opinioesNegativas) {
        this.opinioesNegativas = opinioesNegativas;
    }

    public Map<String, String> getOpinioesPositivas() {
        return opinioesPositivas;
    }

    public void setOpinioesPositivas(Map<String, String> opinioesPositivas) {
        this.opinioesPositivas = opinioesPositivas;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
