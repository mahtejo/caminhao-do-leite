import base.AbstractTest;
import models.Tema;
import models.dao.GenericDAO;
import models.dica.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by orion on 21/03/15.
 */
public class MetaDicaTest extends AbstractTest {

    DicaGenerica dica1, dica2, dica3, dica4;
    List<DicaGenerica> dicas;
    GenericDAO dao = new GenericDAO();
    Tema tema;

    @Before
    public void before() throws Exception {
        Tema tema = new Tema("OO");
        dica1 = new Conselho("admin", tema, "Estude");
        dica2 = new PrecisaSaber("admin", tema, "Tudo");
        dica3 = new DisciplinaUtil("admin", tema, "Programação 2", "Porque sim");
        dica4 = new MaterialUtil("admin", tema, "http://www.google.com.br");
        dicas = new ArrayList<DicaGenerica>();
    }

   /* @Test
    public void deveAdicionarMetaDicas() throws Exception {
        dao.persist(dica1);
        dao.persist(dica2);

        dicas = dao.findAllByClass(DicaGenerica.class);
        assertThat(dicas.size()).isEqualTo(2);

        DicaGenerica metaDica = new MetaDica("admin", "Concordo com tudo", dicas);
        dao.persist(metaDica);

        assertThat(dicas.size()).isEqualTo(2);
    }*/

    @Test
    public void naoDeveCriarMetaDicaVazia(){
        try{
            DicaGenerica metaDica = new MetaDica("admin", "Concordo com tudo", dicas);
            dao.persist(metaDica);
            fail();
        } catch (Exception e) {
        }
        assertThat(dao.findAllByClass(DicaGenerica.class).size()).isEqualTo(0);
    }
}
