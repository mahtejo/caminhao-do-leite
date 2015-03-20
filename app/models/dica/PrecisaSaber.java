package models.dica;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by orion on 19/03/15.
 */
@Entity(name="PrecisaSaber")
public class PrecisaSaber extends Dica {

    @Column
    private String assunto;

    public PrecisaSaber(){}

    public PrecisaSaber(String assunto) throws Exception {
        setAssunto(assunto);
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) throws Exception {
        if (assunto.equals("")){
            throw new Exception("Assunto não deve ser vazio!");
        }
        this.assunto = assunto;
    }

    @Override
    public String toString() {
        return "O que precisa saber para não ter dificuldades: " + getAssunto();
    }
}
