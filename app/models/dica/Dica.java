package models.dica;

import play.Logger;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

import static play.mvc.Controller.flash;

/**
 * Created by orion on 19/03/15.
 */
@Entity(name="Dica")
public abstract class Dica implements Comparable<Dica> {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String usuario;

    @ElementCollection
    @MapKeyColumn
    @Column
    @CollectionTable
    private Map<String, String> opinioesNegativas, opinioesPositivas, conteudoInapropriado;

    public Dica(){
        this.conteudoInapropriado = new HashMap<String, String>();
        this.opinioesNegativas = new HashMap<String, String>();
        this.opinioesPositivas = new HashMap<String, String>();
    }

    public Dica(String usuario){
        this.conteudoInapropriado = new HashMap<String, String>();
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

    public void addOpiniaoPositiva(String usuario) throws Exception {
        if(!isMensagemFixada()) {
            opinioesNegativas.remove(usuario);
            opinioesPositivas.put(usuario, "");
        }
    }

    public void addOpiniaoNegativa(String usuario, String opiniao) throws Exception {
        if(!isMensagemFixada()) {
            if (opiniao.length() > 100) {
                throw new Exception("Erro: Opinião deve conter menos que 100 caracteres!");
            } else if (opiniao.length() == 0) {
                throw new Exception("Erro: Opinião deve conter mais que 0 caracteres!");
            }
            opinioesPositivas.remove(usuario);
            opinioesNegativas.put(usuario, opiniao);
        }
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

    public abstract String getTipo();

    public int compareTo(Dica outraDica){
        if (this.getNumeroConcordaram() < outraDica.getNumeroConcordaram()){
            return -1;
        } else if (this.getNumeroConcordaram() > outraDica.getNumeroConcordaram()){
            return 1;
        } else if (this.getNumeroDiscordaram() > outraDica.getNumeroDiscordaram()){
            return -1;
        } else if (this.getNumeroDiscordaram() < outraDica.getNumeroDiscordaram()){
            return 1;
        } else {
            return 0;
        }
    }

    public void informaConteudoInapropriado(String usuario) throws Exception {
        if(!isMensagemFixada()) {
            conteudoInapropriado.put(usuario, "");
        }
    }

    public void setConteudoInapropriado(Map<String, String> conteudoInapropriado) {
        this.conteudoInapropriado = conteudoInapropriado;
    }

    public Map<String, String> getConteudoInapropriado(){
        return conteudoInapropriado;
    }

    public int numeroConteudoInapropriado(){
        return getConteudoInapropriado().size();
    }

    private boolean isMensagemFixada() throws Exception {
        if (getNumeroConcordaram() >= 20 || getNumeroDiscordaram() >= 20){
            throw new Exception("Erro: Número máximo de discordância deve ser 20");
        }
        return false;
    }
}
