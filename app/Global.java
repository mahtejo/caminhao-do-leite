import models.Tema;
import models.Usuario;
import models.dao.GenericDAO;
import models.dica.*;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;

import java.util.List;

/**
 * Created by orion on 18/03/15.
 */
public class Global extends GlobalSettings {

    private static GenericDAO dao = new GenericDAO();

    @Override
    public void onStart(Application app) {
        Logger.info("Aplicação inicializada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                popularBD();
                dao.flush();
            }
        });
    }

    @Override
    public void onStop(Application app){
        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                Logger.info("Aplicação finalizando...");

                List<Tema> temas = dao.findAllByClass(Tema.class);

                for (Tema tema: temas) {
                    dao.removeById(Tema.class, tema.getId());
                }
            }
        });
    }

    private void popularBD() throws Exception {
        Usuario usuario1 = new Usuario("Admin", "admin", "admin");
        Usuario usuario2 = new Usuario("Órion Winter", "orion", "orion");
        dao.persist(usuario1);
        dao.persist(usuario2);

        Tema tema1 = new Tema("Análise x Design");
        tema1.addDica(new Conselho("admin", tema1, "Estude bastante!"));
        DicaGenerica dica1 = new MaterialUtil("admin", tema1, "http://www.google.com");
        dica1.addOpiniaoNegativa("orion", "Engraçado, fera!");
        DicaGenerica dica2 = new PrecisaSaber("orion", tema1, "tudo");
        dica2.addOpiniaoPositiva("admin");
        tema1.addDica(dica1);
        tema1.addDica(dica2);
        dao.persist(tema1);

        Tema tema2 = new Tema("OO");
        tema2.addDica(new PrecisaSaber("admin", tema2, "Programar em Java!"));
        dao.persist(tema2);

        Tema tema3 = new Tema("GRASP");
        dao.persist(tema3);

        Tema tema4 = new Tema("GoF");
        dao.persist(tema4);

        Tema tema5 = new Tema("Arquitetura");
        dao.persist(tema5);

        Tema tema6 = new Tema("Play");
        DicaGenerica dica = new Conselho("admin", tema6, "Não se estresse, é assim mesmo!");
        dica.addOpiniaoPositiva("admin");
        tema6.addDica(dica);
        dao.persist(tema6);

        Tema tema7 = new Tema("JS");
        dao.persist(tema7);

        Tema tema8 = new Tema("HTML+CSS+Bootstrap");
        dao.persist(tema8);

        Tema tema9 = new Tema("Heroku");
        dao.persist(tema9);

        Tema tema10 = new Tema("Labs");
        dao.persist(tema10);

        Tema tema11 = new Tema("Minitestes");
        dao.persist(tema11);

        Tema tema12 = new Tema("Projeto");
        dao.persist(tema12);

        Logger.debug("Terminando de inserir os dados!");
    }
}
