import models.Tema;
import models.dao.GenericDAO;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * Created by orion on 18/03/15.
 */
public class Global extends GlobalSettings {

    private static final int TEMA = 0;
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

    private void popularBD() {
        String csvFile = Play.application().getFile("/conf/temasFile.csv").getAbsolutePath();
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            String[] info = line.split(",");

            while ((line = br.readLine()) != null) {
                info = line.split(",");
                Tema tema = new Tema(info[TEMA]);

                Logger.debug("Inserindo o tema - " + tema.getNome());

                dao.persist(tema);
            }
            Logger.debug("Inseriu no bd");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
