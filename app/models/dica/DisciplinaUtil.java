package models.dica;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by orion on 19/03/15.
 */
@Entity(name="DisciplinaUtil")
public class DisciplinaUtil extends Dica {

    @Column
    private String nome;

    @Column
    private String razao;

    public DisciplinaUtil(){}

    public DisciplinaUtil(String nome, String razao) throws Exception {
        setNome(nome);
        setRazao(razao);
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) throws Exception {
        if (razao.equals("")){
            throw new Exception("Razão não deve ser vazia!");
        }
        this.razao = razao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome.equals("")){
            throw new Exception("Erro: Nome não deve ser vazio!");
        }
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Nome:" + getNome() + "Razão: " + getRazao();
    }

    @Override
    public String getTipo() {
        return "Disciplina útil";
    }
}
