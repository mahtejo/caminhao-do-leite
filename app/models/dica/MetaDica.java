package models.dica;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orion on 21/03/15.
 */
@Entity(name="MetaDica")
public class MetaDica extends DicaGenerica {

    @ManyToMany(cascade= CascadeType.ALL)
    private List<DicaGenerica> dicas;

    @Column
    private String comentario;

    public MetaDica(){
        dicas = new ArrayList<DicaGenerica>();
    }

    public MetaDica(String usuario, String comentario, List<DicaGenerica> dicas) throws Exception {
        super(usuario);
        setComentario(comentario);
        setDicas(dicas);
    }

    @Override
    public String toString() {
        return getComentario();
    }

    @Override
    public String getTipo() {
        return "Meta dica";
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) throws Exception {
        if (comentario.length() == 0){
            throw new Exception("Erro: Comentário não deve ser vazio!");
        }
        this.comentario = comentario;
    }

    public List<DicaGenerica> getDicas() {
        return dicas;
    }

    public void setDicas(List<DicaGenerica> dicas) throws Exception {
        if (dicas.size() == 0){
            throw new Exception("Erro: Uma meta dica deve deve ter pelo menos uma dica");
        }
        this.dicas = dicas;
    }
}
