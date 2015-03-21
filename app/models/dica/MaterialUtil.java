package models.dica;

import models.Tema;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by orion on 19/03/15.
 */
@Entity(name="MaterialUtil")
public class MaterialUtil extends Dica {

    @Column
    private String material;

    public MaterialUtil(){}

    public MaterialUtil(String usuario, Tema tema, String material) throws Exception {
        super(usuario, tema);
        setMaterial(material);
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) throws Exception {
        if (!verificaAutenticidadeDoMaterial(material)){
            throw new Exception("Erro: URL do material deve começar com http:// e acabar com .com, .com.br, .edu ou .edu.br");
        }
        this.material = material;
    }

    private boolean verificaAutenticidadeDoMaterial(String material){
        if (material.startsWith("http://") &&
                (material.startsWith(".com", material.length()-4)
                || material.startsWith(".com.br", material.length()-7)
                || material.startsWith(".edu", material.length()-4)
                || material.startsWith(".edu.br", material.length()-7))){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getMaterial();
    }

    @Override
    public String getTipo() {
        return "Material útil";
    }
}
