import base.AbstractTest;
import models.Tema;
import models.dao.GenericDAO;
import models.dica.*;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by orion on 19/03/15.
 */
public class DicaTest extends AbstractTest{
    GenericDAO dao = new GenericDAO();
    List<Dica> dicas;

    @Test
    public void deveCriarDicaDoTipoConselho() throws Exception{
        Dica dica = new Conselho("Usuário", "Estude o play!");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("Estude o play!");
    }

    @Test
    public void naoDeveCriarConselhoVazio(){
        try {
            Dica dica = new Conselho("", "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveCriarDicaDeDisciplinaUtil() throws Exception {
        Dica dica = new DisciplinaUtil("Usuário", "Programação 2", "Padrões de projeto");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("Nome: Programação 2\n Razão: Padrões de projeto");
    }

    @Test
    public void naoDeveCriarDisciplinaUtilVazia(){
        try {
            Dica dica = new DisciplinaUtil("", "", "Padrões de projeto");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try {
            Dica dica = new DisciplinaUtil("", "Programação 2", "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try {
            Dica dica = new DisciplinaUtil("", "", "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveCriarDicaDeMaterialUtil() throws Exception {
        Dica dica = new MaterialUtil("Usuário", "http://www.playframework.com");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("http://www.playframework.com");
    }

    @Test
    public void naoDeveCriarDicaDeMaterialUtilInvalido(){
        try{
            Dica dica = new MaterialUtil("", "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try{
            Dica dica = new MaterialUtil("Usuário", "https://www.playframework.com");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try{
            Dica dica = new MaterialUtil("Usuário", "http://www.playframework.net");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try{
            Dica dica = new MaterialUtil("Usuário", "https://www.playframework.net");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveCriarDicaParaNaoTerDificuldade() throws Exception {
        Dica dica = new PrecisaSaber("Usuário", "Saber programar em Java muito bem!");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("Saber programar em Java muito bem!");
    }

    @Test
    public void naoDeveCriarDicaParaNaoTerDificuldadeVazia(){
        try{
            Dica dica = new PrecisaSaber("Usuário", "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e) {
            dicas = dao.findAllByClass(Dica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveAdicionarOpiniao() throws Exception {
        Dica dica = new PrecisaSaber("Usuário", "Saber programar em Java muito bem!");
        dica.addOpiniaoNegativa("Joao", "Sabe de nada");
        dica.addOpiniaoPositiva("Maria");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        dica = dicas.get(0);

        assertThat(dica.getNumeroConcordaram()).isEqualTo(1);
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(1);
        assertThat(dica.concordancia()).isEqualTo(0.5);
    }

    @Test
    public void devePoderMudarDeOpiniao() throws Exception {
        Dica dica = new PrecisaSaber("Usuário", "Saber programar em Java muito bem!");
        dica.addOpiniaoNegativa("Joao", "Sabe de nada");
        assertTrue(dica.getOpinioesNegativas().containsKey("Joao"));
        dica.addOpiniaoPositiva("Joao");
        assertFalse(dica.getOpinioesNegativas().containsKey("Joao"));
        assertTrue(dica.getOpinioesPositivas().containsKey("Joao"));
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(Dica.class);

        dica = dicas.get(0);

        assertThat(dica.getNumeroConcordaram()).isEqualTo(1);
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(0);
        assertThat(dica.concordancia()).isEqualTo(1);
    }

    @Test
    public void naoDevePoderInserirComentarioNegativoVazio(){
        try{
            Dica dica = new PrecisaSaber("Usuário", "Saber programar em Java muito bem!");
            dica.addOpiniaoNegativa("Joao", "");
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void naoDevePoderInserirComentarioNegativoMaisDe100Caracteres(){
        try{
            Dica dica = new PrecisaSaber("Usuário", "Saber programar em Java muito bem!");
            dica.addOpiniaoNegativa("Joao", "çalskdfjasçldkjfasçldkjfasçlkdjfasçldjfaslkdjfasdlkjfçasdjfçlajsdflasjkdfçlasdfjasdlçfjsadçlfkjasdlçfkajsdçfljsdalfkjasdçlfkjasçldfkjsdaçflkjasdlçfjasldkfaslkçdfçasdflasdfçlkjsadfçljsadfçlaksdfçlasjdfçlasdkjf");
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void devePoderMudarComentarioNegativo() throws Exception {
        Dica dica = new PrecisaSaber("Usuário", "Saber programar em Java muito bem!");
        dica.addOpiniaoNegativa("Malandrão", "Gostei não");
        assertThat(dica.getOpinioesNegativas().get("Malandrão")).isEqualTo("Gostei não");
        dica.addOpiniaoNegativa("Malandrão", "Sabe de nada");
        assertThat(dica.getOpinioesNegativas().get("Malandrão")).isEqualTo("Sabe de nada");
    }

    @Test
    public void devePegarDicasOrdenadasInversamente() throws Exception {
        Tema tema = new Tema("OO");
        Conselho conselho = new Conselho("joana", "Estude");
        conselho.addOpiniaoPositiva("belarmino");
        conselho.addOpiniaoPositiva("rudckleidson");
        conselho.addOpiniaoNegativa("sinfronio", "Maiisss ou menoss!");

        PrecisaSaber ps = new PrecisaSaber("joao", "Um pouco de tudo");
        ps.addOpiniaoPositiva("josefina");
        ps.addOpiniaoNegativa("Paulo", "Muito vago!");
        ps.addOpiniaoNegativa("Marcio", "Não precisa saber de tuuuudo!");

        DisciplinaUtil disciplinaUtil = new DisciplinaUtil("maria", "Programação 2", "Padrões de projeto");
        disciplinaUtil.addOpiniaoPositiva("Moacir");
        disciplinaUtil.addOpiniaoPositiva("Motumbo");
        disciplinaUtil.addOpiniaoPositiva("Felipe");
        disciplinaUtil.addOpiniaoPositiva("Oscar");

        Conselho conselho2 = new Conselho("Bonifácio", "Faça o projeto de última hora! kkkk");
        conselho2.addOpiniaoNegativa("Paulo", "Pensee");
        conselho2.addOpiniaoNegativa("Marcio", "kkkkkkkkkk");

        tema.addDica(conselho);
        tema.addDica(ps);
        tema.addDica(disciplinaUtil);
        tema.addDica(conselho2);

        dicas = tema.getDicas();
        assertThat(dicas.get(0)).isEqualTo(disciplinaUtil);
        assertThat(dicas.get(1)).isEqualTo(conselho);
        assertThat(dicas.get(2)).isEqualTo(ps);
        assertThat(dicas.get(3)).isEqualTo(conselho2);
    }
}
