package controllers;

import models.Tema;
import models.dao.GenericDAO;
import models.dica.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;

import static play.mvc.Controller.flash;
import static play.mvc.Controller.session;

/**
 * Created by orion on 19/03/15.
 */
public class Dicas {

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
                tema.addDica(new Conselho(session().get("user"), conselho));
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
                tema.addDica(new MaterialUtil(session().get("user"), url));
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
                tema.addDica(new DisciplinaUtil(session().get("user"), disciplina, razao));
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
                tema.addDica(new PrecisaSaber(session().get("user"), assunto));
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

    @Transactional
    public static Result addOpiniao(long idTema, long idDica, int opiniao) {
        DynamicForm filledForm = Form.form().bindFromRequest();
        if (filledForm.hasErrors()){
            flash("message", "Formulário invalido!");
            return Temas.temas(400, idTema);
        }
        Dica dica = dao.findByEntityId(Dica.class, idDica);
        if (opiniao == 0){
            String justificativa = filledForm.get("justificativa");
            try {
                dica.addOpiniaoNegativa(session().get("user"), justificativa);
                dao.merge(dica);
                dao.flush();
                return Temas.temas(200, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(400, idTema);
            }
        } else if (opiniao == 1){
            try {
                dica.addOpiniaoPositiva(session().get("user"));
                dao.merge(dica);
                dao.flush();
                return Temas.temas(200, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(400, idTema);
            }
        } else {
            flash("message", "Erro: Não foi possível adicionar opinião!");
            return Temas.temas(400, idTema);
        }
    }

    @Transactional
    public static Result reportarConteudoInapropriado(long idTema, long idDica) {
        Tema tema = dao.findByEntityId(Tema.class, idTema);
        Dica dica = dao.findByEntityId(Dica.class, idDica);

        try {
            tema.informaConteudoInapropriado(dica, session().get("user"));
            return Temas.temas(200, idTema);
        } catch (Exception e) {
            flash("message", e.getMessage());
            return Temas.temas(400, idTema);
        }
    }
}
