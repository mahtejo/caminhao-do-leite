package models.dica;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by orion on 19/03/15.
 */
@Entity(name="Conselho")
public class Conselho extends Dica {

    @Column
    private String conselho;

    public Conselho(){}

    public Conselho(String conselho) throws Exception {
        setConselho(conselho);
    }

    public String getConselho() {
        return conselho;
    }

    public void setConselho(String conselho) throws Exception {
        if (conselho.equals("")){
            throw new Exception("Conselho não deve ser vazio!");
        }
        this.conselho = conselho;
    }

    @Override
    public String toString() {
        return "Conselho: " + conselho;
    }
}
