package controllers;

import models.Tema;
import models.dao.GenericDAO;
import models.dica.Conselho;
import models.dica.DisciplinaUtil;
import models.dica.MaterialUtil;
import models.dica.PrecisaSaber;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Controller.flash;

/**
 * Created by orion on 19/03/15.
 */
public class Dica {

    private static GenericDAO dao = new GenericDAO();

    @Transactional
    public static Result addDica(Long idTema) {
        DynamicForm filledForm = Form.form().bindFromRequest();
        if (filledForm.hasErrors()){
            flash("message", "Formulário invalido!");
            return Temas.temas(400, idTema);
        }
        if (filledForm.get("conselho") != null) {
            try {
                Tema tema = dao.findByEntityId(Tema.class, idTema);
                String conselho = filledForm.get("conselho");
                tema.addDica(new Conselho(conselho));
                dao.merge(tema);
                dao.flush();

                Logger.debug("Adicionou dica");
                return Temas.temas(200, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(400, idTema);
            }
        } else if (filledForm.get("url") != null){
            try {
                Tema tema = dao.findByEntityId(Tema.class, idTema);
                String url = filledForm.get("url");
                tema.addDica(new MaterialUtil(url));
                dao.merge(tema);
                dao.flush();

                Logger.debug("Adicionou dica");
                return Temas.temas(200, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(400, idTema);
            }
        } else if (filledForm.get("disciplina") != null){
            try {
                Tema tema = dao.findByEntityId(Tema.class, idTema);
                String disciplina = filledForm.get("disciplina");
                String razao = filledForm.get("razao");
                tema.addDica(new DisciplinaUtil(disciplina, razao));
                dao.merge(tema);
                dao.flush();

                Logger.debug("Adicionou dica");
                return Temas.temas(200, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(400, idTema);
            }
        } else if (filledForm.get("assunto") != null){
            try {
                Tema tema = dao.findByEntityId(Tema.class, idTema);
                String assunto = filledForm.get("assunto");
                tema.addDica(new PrecisaSaber(assunto));
                dao.merge(tema);
                dao.flush();

                Logger.debug("Adicionou dica");
                return Temas.temas(200, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(400, idTema);
            }
        }
        flash("message", "Ocorreu um erro");
        return Temas.temas(400, idTema);
    }
}
