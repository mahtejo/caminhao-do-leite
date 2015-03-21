import base.AbstractTest;
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

    Dica dica1, dica2, dica3, dica4;
    List<Dica> dicas;
    GenericDAO dao = new GenericDAO();

    @Before
    public void before() throws Exception {
        dica1 = new Conselho("admin", "Estude");
        dica2 = new PrecisaSaber("admin", "Tudo");
        dica3 = new DisciplinaUtil("admin", "Programação 2", "Porque sim");
        dica4 = new MaterialUtil("admin", "http://www.google.com.br");
        dicas = new ArrayList<Dica>();
    }

    @Test
    public void deveAdicionarMetaDicas() throws Exception {
        dao.persist(dica1);
        dao.persist(dica2);

        dicas = dao.findAllByClass(Dica.class);
        assertThat(dicas.size()).isEqualTo(2);

        Dica metaDica = new MetaDica("admin", "Concordo com tudo", dicas);
        dao.persist(metaDica);

        List<MetaDica> md = dao.findAllByClass(MetaDica.class);
        assertThat(md.get(0).getDicas().size()).isEqualTo(2);
    }

    @Test
    public void naoDeveCriarMetaDicaVazia(){
        try{
            Dica metaDica = new MetaDica("admin", "Concordo com tudo", dicas);
            dao.persist(metaDica);
            fail();
        } catch (Exception e) {
        }
        assertThat(dao.findAllByClass(MetaDica.class).size()).isEqualTo(0);
    }
}
