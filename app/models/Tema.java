package models;

import javax.persistence.*;
import java.util.*;

/**
 * Created by orion on 18/03/15.
 */

@Entity(name="Tema")
public class Tema {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String nome;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn
    private List<DicaGenerica> dicas;

    @ElementCollection
    @MapKeyColumn
    @Column
    @CollectionTable
    private Map<String, Integer> dificuldadeUsuarios;

    private static final int DIFICULDADE_MINIMA = -2;
    private static final int DIFICULDADE_MAXIMA = 2;
    private static final int CONTEUDO_INAPROPRIADO_MAXIMO = 3;

    public Tema(){
        dicas = new ArrayList<DicaGenerica>();
        dificuldadeUsuarios = new HashMap<String, Integer>();
    }

    public Tema(String nome){
        dicas = new ArrayList<DicaGenerica>();
        dificuldadeUsuarios = new HashMap<String, Integer>();
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void addDica(DicaGenerica dica) {
        dicas.add(dica);
    }

    public List<DicaGenerica> getDicas() {
        Collections.sort(dicas);
        Collections.reverse(dicas);
        return dicas;
    }

    public Map<String, Integer> getDificuldadeUsuarios() {
        return dificuldadeUsuarios;
    }

    public void setdificuldadeUsuarios(Map<String, Integer> dificuldadeUsuarios) {
        this.dificuldadeUsuarios = dificuldadeUsuarios;
    }

    public void addDificuldade(String usuario, int dificuldade) throws Exception {
        if(usuario == null || dificuldade < DIFICULDADE_MINIMA || dificuldade > DIFICULDADE_MAXIMA){
            throw new Exception("Erro: Dificuldade deve ser entre -2 e 2!");
        }
        dificuldadeUsuarios.put(usuario, dificuldade);
    }

    public String getDificuldadeMediaFormatada(){
        return String.format("%.2f", getDificuldadeMedia());
    }

    public double getDificuldadeMedia(){
        int numeroDeUsuarios = dificuldadeUsuarios.size();
        if (numeroDeUsuarios == 0){
            return 0;
        }
        double soma = 0;
        for (Integer i: dificuldadeUsuarios.values()){
            soma += i;
        }
        return soma/numeroDeUsuarios;
    }

    public String getDificuldadeMedianaFormatada(){
        return String.format("%.2f", getDificuldadeMediana());
    }

    public double getDificuldadeMediana(){
        int numeroDeUsuarios = dificuldadeUsuarios.size();
        if (numeroDeUsuarios == 0){
            return 0;
        }
        List<Integer> dificuldades = new LinkedList<Integer>();
        for (Integer i: dificuldadeUsuarios.values()){
            dificuldades.add(i);
        }
        return mediana(dificuldades);
    }

    private double mediana(List<Integer> lista){
        Collections.sort(lista);
        int n = lista.size();
        if (n % 2 != 0){
            return lista.get((n - 1 ) / 2);
        } else {
            return (lista.get(n/2) + lista.get((n/2)-1)) / 2.0;
        }
    }

    public void informaConteudoInapropriado(DicaGenerica dica, String usuario) throws Exception {
        dica.informaConteudoInapropriado(usuario);
        if (dica.numeroConteudoInapropriado() >= CONTEUDO_INAPROPRIADO_MAXIMO){
            dicas.remove(dica);
        }
    }
}
