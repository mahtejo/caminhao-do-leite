import base.AbstractTest;
import models.dao.GenericDAO;
import models.dica.*;
import models.opiniao.OpiniaoNegativa;
import models.opiniao.OpiniaoPositiva;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by orion on 19/03/15.
 */
public class DicaTest extends AbstractTest{
    GenericDAO dao = new GenericDAO();
    List<Dica> dicas;

    @Test
    public void deveCriarDicaDoTipoConselho() throws Exception{
        Dica dica = new Conselho("Estude o play!");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("Conselho: Estude o play!");
    }

    @Test
    public void naoDeveCriarConselhoVazio(){
        try {
            Dica dica = new Conselho("");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveCriarDicaDeDisciplinaUtil() throws Exception {
        Dica dica = new DisciplinaUtil("Programação 2", "Padrões de projeto");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("Disciplina útil: Programação 2 - Razão: Padrões de projeto");
    }

    @Test
    public void naoDeveCriarDisciplinaUtilVazia(){
        try {
            Dica dica = new DisciplinaUtil("", "Padrões de projeto");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try {
            Dica dica = new DisciplinaUtil("Programação 2", "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try {
            Dica dica = new DisciplinaUtil("", "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveCriarDicaDeMaterialUtil() throws Exception {
        Dica dica = new MaterialUtil("http://www.playframework.com");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("Material útil: http://www.playframework.com");
    }

    @Test
    public void naoDeveCriarDicaDeMaterialUtilInvalido(){
        try{
            Dica dica = new MaterialUtil("");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try{
            Dica dica = new MaterialUtil("https://www.playframework.com");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try{
            Dica dica = new MaterialUtil("http://www.playframework.net");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try{
            Dica dica = new MaterialUtil("https://www.playframework.net");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveCriarDicaParaNaoTerDificuldade() throws Exception {
        Dica dica = new PrecisaSaber("Saber programar em Java muito bem!");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("O que precisa saber para não ter dificuldades: Saber programar em Java muito bem!");
    }

    @Test
    public void naoDeveCriarDicaParaNaoTerDificuldadeVazia(){
        try{
            Dica dica = new PrecisaSaber("");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e) {
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveAdicionarOpiniaoADica() throws Exception {
        Dica dica = new PrecisaSaber("Programar muito bem");
        /*dao.persist(dica);
        dao.flush();*/

        dica.addOpiniao("usuario", new OpiniaoPositiva());
        dica.addOpiniao("usuario2", new OpiniaoNegativa("Não concordo"));
        /*dao.merge(dica);
        dao.flush();
*/
        //assertThat(dao.findAllByClass(Dica.class).size()).isEqualTo(1);
        assertThat(dica.getNumeroConcordaram()).isEqualTo(1);
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(1);
        assertThat(dica.concordancia()).isEqualTo(0.5);
    }

    @Test
    public void devePoderMudarDeOpiniao() throws Exception{
        Dica dica = new PrecisaSaber("Programar muito bem");
        /*dao.persist(dica);
        dao.flush();*/

        dica.addOpiniao("usuario", new OpiniaoPositiva());
        assertThat(dica.getNumeroConcordaram()).isEqualTo(1);
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(0);
        assertThat(dica.concordancia()).isEqualTo(1);
        dica.addOpiniao("usuario", new OpiniaoNegativa("Não concordo"));
        assertThat(dica.getNumeroConcordaram()).isEqualTo(0);
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(1);
        assertThat(dica.concordancia()).isEqualTo(0);
    }
}
