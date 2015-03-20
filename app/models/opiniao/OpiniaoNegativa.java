package models.opiniao;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by orion on 20/03/15.
 */
@Entity(name="OpiniaoNegativa")
public class OpiniaoNegativa extends Opiniao{

    @Column
    private String comentario;

    public OpiniaoNegativa(String comentario) throws Exception {
        super(false);
        setComentario(comentario);
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) throws Exception {
        if (comentario.length() > 100){
            throw new Exception("Erro: Comentário deve ter menos de 100 caracteres!");
        }
        this.comentario = comentario;
    }
}
