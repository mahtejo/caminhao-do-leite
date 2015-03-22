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
    private final int ZERO = 0;
    private final int UM = 1;
    private final int MENOS_DOIS = -2;
    private final int MAIS_DOIS = 2;
    private final int TRES = 3;
    private final double DOIS_DEC = 2.0;


    public Tema(){
        dicas = new ArrayList<DicaGenerica>();
        dificuldadeUsuarios = new HashMap<String, Integer>();
    }

    public Tema(String nome){
        dicas = new ArrayList<DicaGenerica>();
        dificuldadeUsuarios = new HashMap<String, Integer>();
        setNome(nome);
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
        if(usuario == null || dificuldade < MENOS_DOIS || dificuldade > MAIS_DOIS){
            throw new Exception("Erro: Dificuldade deve ser entre -2 e 2!");
        }
        dificuldadeUsuarios.put(usuario, dificuldade);
    }

    public String getDificuldadeMediaFormatada(){
        return String.format("%.2f", getDificuldadeMedia());
    }

    public double getDificuldadeMedia(){
        int numeroDeUsuarios = dificuldadeUsuarios.size();
        if (numeroDeUsuarios == ZERO){
            return ZERO;
        }
        double soma = ZERO;
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
        if (numeroDeUsuarios == ZERO){
            return ZERO;
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
        if (n % MAIS_DOIS != ZERO){
            return lista.get((n - UM ) / MAIS_DOIS);
        } else {
            return (lista.get(n/MAIS_DOIS) + lista.get((n/MAIS_DOIS)-UM)) / DOIS_DEC;
        }
    }

    public void informaConteudoInapropriado(DicaGenerica dica, String usuario) throws Exception {
        dica.informaConteudoInapropriado(usuario);
        if (dica.numeroConteudoInapropriado() >= TRES){
            dicas.remove(dica);
        }
    }
}
