import base.AbstractTest;
import models.*;
import models.dao.GenericDAO;
import org.junit.Before;
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
    List<DicaGenerica> dicas;
    Tema tema;

    @Before
    public void before(){
        Tema tema = new Tema("OO");
    }

    @Test
    public void deveCriarDicaDoTipoConselho() throws Exception{
        DicaGenerica dica = new Conselho("Usuário", tema, "Estude o play!");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(DicaGenerica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("Estude o play!");
    }

    @Test
    public void naoDeveCriarConselhoVazio(){
        try {
            DicaGenerica dica = new Conselho("", tema, "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(DicaGenerica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveCriarDicaDeDisciplinaUtil() throws Exception {
        DicaGenerica dica = new DisciplinaUtil("Usuário", tema, "Programação 2", "Padrões de projeto");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(DicaGenerica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("Nome: Programação 2\nRazão: Padrões de projeto\n");
    }

    @Test
    public void naoDeveCriarDisciplinaUtilVazia(){
        try {
            DicaGenerica dica = new DisciplinaUtil("", tema, "", "Padrões de projeto");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(DicaGenerica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try {
            DicaGenerica dica = new DisciplinaUtil("", tema, "Programação 2", "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(DicaGenerica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try {
            DicaGenerica dica = new DisciplinaUtil("", tema, "", "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(DicaGenerica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveCriarDicaDeMaterialUtil() throws Exception {
        DicaGenerica dica = new MaterialUtil("Usuário", tema, "http://www.playframework.com");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(DicaGenerica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("http://www.playframework.com");
    }

    @Test
    public void naoDeveCriarDicaDeMaterialUtilInvalido(){
        try{
            DicaGenerica dica = new MaterialUtil("", tema, "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(DicaGenerica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try{
            DicaGenerica dica = new MaterialUtil("Usuário", tema, "https://www.playframework.com");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(DicaGenerica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try{
            DicaGenerica dica = new MaterialUtil("Usuário", tema, "http://www.playframework.net");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(DicaGenerica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }

        try{
            DicaGenerica dica = new MaterialUtil("Usuário", tema, "https://www.playframework.net");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e){
            dicas = dao.findAllByClass(DicaGenerica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveCriarDicaParaNaoTerDificuldade() throws Exception {
        DicaGenerica dica = new PrecisaSaber("Usuário", tema, "Saber programar em Java muito bem!");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(DicaGenerica.class);

        assertThat(dicas.size()).isEqualTo(1);
        assertThat(dicas.get(0).toString()).isEqualTo("Saber programar em Java muito bem!");
    }

    @Test
    public void naoDeveCriarDicaParaNaoTerDificuldadeVazia(){
        try{
            DicaGenerica dica = new PrecisaSaber("Usuário", tema, "");
            dao.persist(dica);
            dao.flush();
        } catch (Exception e) {
            dicas = dao.findAllByClass(DicaGenerica.class);
            assertThat(dicas.size()).isEqualTo(0);
        }
    }

    @Test
    public void deveAdicionarOpiniao() throws Exception {
        DicaGenerica dica = new PrecisaSaber("Usuário", tema, "Saber programar em Java muito bem!");
        dica.addOpiniaoNegativa("Joao", "Sabe de nada");
        dica.addOpiniaoPositiva("Maria");
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(DicaGenerica.class);

        dica = dicas.get(0);

        assertThat(dica.getNumeroConcordaram()).isEqualTo(1);
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(1);
        assertThat(dica.concordancia()).isEqualTo(0.5);
    }

    @Test
    public void devePoderMudarDeOpiniao() throws Exception {
        DicaGenerica dica = new PrecisaSaber("Usuário", tema, "Saber programar em Java muito bem!");
        dica.addOpiniaoNegativa("Joao", "Sabe de nada");
        assertTrue(dica.getOpinioesNegativas().containsKey("Joao"));
        dica.addOpiniaoPositiva("Joao");
        assertFalse(dica.getOpinioesNegativas().containsKey("Joao"));
        assertTrue(dica.getOpinioesPositivas().containsKey("Joao"));
        dao.persist(dica);
        dao.flush();

        dicas = dao.findAllByClass(DicaGenerica.class);

        dica = dicas.get(0);

        assertThat(dica.getNumeroConcordaram()).isEqualTo(1);
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(0);
        assertThat(dica.concordancia()).isEqualTo(1);
    }

    @Test
    public void naoDevePoderInserirComentarioNegativoVazio(){
        try{
            DicaGenerica dica = new PrecisaSaber("Usuário", tema, "Saber programar em Java muito bem!");
            dica.addOpiniaoNegativa("Joao", "");
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void naoDevePoderInserirComentarioNegativoMaisDe100Caracteres(){
        try{
            DicaGenerica dica = new PrecisaSaber("Usuário", tema, "Saber programar em Java muito bem!");
            dica.addOpiniaoNegativa("Joao", "çalskdfjasçldkjfasçldkjfasçlkdjfasçldjfaslkdjfasdlkjfçasdjfçlajsdflasjkdfçlasdfjasdlçfjsadçlfkjasdlçfkajsdçfljsdalfkjasdçlfkjasçldfkjsdaçflkjasdlçfjasldkfaslkçdfçasdflasdfçlkjsadfçljsadfçlaksdfçlasjdfçlasdkjf");
            fail();
        } catch (Exception e) {
        }
    }

    @Test
    public void devePoderMudarComentarioNegativo() throws Exception {
        DicaGenerica dica = new PrecisaSaber("Usuário", tema, "Saber programar em Java muito bem!");
        dica.addOpiniaoNegativa("Malandrão", "Gostei não");
        assertThat(dica.getOpinioesNegativas().get("Malandrão")).isEqualTo("Gostei não");
        dica.addOpiniaoNegativa("Malandrão", "Sabe de nada");
        assertThat(dica.getOpinioesNegativas().get("Malandrão")).isEqualTo("Sabe de nada");
    }

    @Test
    public void devePegarDicasOrdenadasInversamente() throws Exception {
        tema = new Tema("OO");
        DicaGenerica conselho = new Conselho("joana", tema, "Estude");
        conselho.addOpiniaoPositiva("belarmino");
        conselho.addOpiniaoPositiva("rudckleidson");
        conselho.addOpiniaoNegativa("sinfronio", "Maiisss ou menoss!");

        DicaGenerica ps = new PrecisaSaber("joao", tema, "Um pouco de tudo");
        ps.addOpiniaoPositiva("josefina");
        ps.addOpiniaoNegativa("Paulo", "Muito vago!");
        ps.addOpiniaoNegativa("Marcio", "Não precisa saber de tuuuudo!");

        DicaGenerica disciplinaUtil = new DisciplinaUtil("maria", tema, "Programação 2", "Padrões de projeto");
        disciplinaUtil.addOpiniaoPositiva("Moacir");
        disciplinaUtil.addOpiniaoPositiva("Motumbo");
        disciplinaUtil.addOpiniaoPositiva("Felipe");
        disciplinaUtil.addOpiniaoPositiva("Oscar");

        DicaGenerica conselho2 = new Conselho("Bonifácio", tema, "Faça o projeto de última hora! kkkk");
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

    @Test
    public void deveAdicionarNoMaximo20Concordancias() throws Exception {
        DicaGenerica dica = new Conselho("usuario", tema, "conselho");

        try {
            for (int i = 0; i < 21; i++) {
                dica.addOpiniaoPositiva("usuario" + i);
            }
        } catch (Exception e){
        }
        assertThat(dica.getNumeroConcordaram()).isEqualTo(20);
    }

    @Test
    public void deveAdicionarNoMaximo20Discordancias() throws Exception {
        DicaGenerica dica = new Conselho("usuario", tema, "conselho");
        try {
            for (int i = 0; i < 21; i++) {
                dica.addOpiniaoNegativa("usuario"+i, "opinião");
            }
        } catch (Exception e) {
        }
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(20);
    }

    @Test
    public void naoDeveExcluirADicaDepoisDe20ConcordanciasOuDiscordancias() throws Exception {
        tema = new Tema("OO");
        DicaGenerica dica = new Conselho("usuario", tema, "conselho");
        tema.addDica(dica);
        tema.informaConteudoInapropriado(dica, "admon");
        for (int i = 0; i < 19; i++) {
            dica.addOpiniaoPositiva("usuario"+i);
        }
        for (int i = 20; i < 40; i++) {
            dica.addOpiniaoNegativa("usuario"+i, "opinião");
        }
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(20);
        assertThat(dica.getNumeroConcordaram()).isEqualTo(19);
        try{
            tema.informaConteudoInapropriado(dica, "admin");
        } catch (Exception e){
        }
        assertThat(dica.numeroConteudoInapropriado()).isEqualTo(1);
        assertThat(dica.getNumeroDiscordaram()).isEqualTo(20);
        assertThat(dica.getNumeroConcordaram()).isEqualTo(19);
    }
}
