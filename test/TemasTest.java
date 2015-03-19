import base.AbstractTest;
import models.Tema;
import models.dao.GenericDAO;
import models.dica.Dica;
import models.dica.PrecisaSaber;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by orion on 18/03/15.
 */
public class TemasTest extends AbstractTest{

    GenericDAO dao = new GenericDAO();
    List<Tema> temas = new ArrayList<Tema>();

    @Test
    public void deveIniciarComOBancoVazio(){
        temas = dao.findAllByClass(Tema.class);

        assertThat(temas.size()).isEqualTo(0);
    }

    @Test
    public void deveInserirTemasDaDisciplina(){
        Tema tema = new Tema("OO");
        dao.persist(tema);
        dao.flush();

        temas = dao.findAllByClass(Tema.class);
        assertThat(temas.size()).isEqualTo(1);
    }

    @Test
    public void deveInserirDicasEmTema(){
        Tema tema = new Tema("OO");
        Dica semDificuldades = new PrecisaSaber();
        tema.addDica(semDificuldades);
        dao.persist(tema);
        dao.flush();

        temas = dao.findAllByClass(Tema.class);
        assertThat(temas.get(0).getDicas().size()).isEqualTo(1);
    }
}
