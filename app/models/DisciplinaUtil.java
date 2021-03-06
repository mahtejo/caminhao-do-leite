package models;

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

    public DisciplinaUtil(String usuario, Tema tema, String nome, String razao) throws Exception {
        super(usuario, tema);
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
        String multilineString = String.format("%s\n%s\n","Nome: " + getNome(), "Razão: " + getRazao());
          return multilineString;

    }

    @Override
    public String getTipo() {
        return "Disciplina útil";
    }
}
