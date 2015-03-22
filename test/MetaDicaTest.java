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

    List<DicaGenerica> dicas;
    GenericDAO dao = new GenericDAO();

    @Test
    public void deveAdicionarMetaDicas() throws Exception {
        Tema tema = new Tema("OO");
        Dica dica1 = new Conselho("admin", tema, "Estude");
        Dica dica2 = new PrecisaSaber("admin", tema, "Tudo");
        tema.addDica(dica1);
        tema.addDica(dica2);

        dao.persist(tema);

        dicas = dao.findAllByClass(DicaGenerica.class);
        assertThat(dicas.size()).isEqualTo(2);

        DicaGenerica metaDica = new MetaDica("admin", "Concordo com tudo", dicas);
        dao.persist(metaDica);

        dicas = dao.findAllByClass(DicaGenerica.class);

        assertThat(dicas.size()).isEqualTo(3);
    }

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
