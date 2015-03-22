package controllers;

import models.*;
import models.dao.GenericDAO;
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
    private static final int BADREQUEST = 400;
    private static final int OK = 200;

    @Transactional
    public static Result addDica(Long idTema) {
        DynamicForm filledForm = Form.form().bindFromRequest();
        if (filledForm.hasErrors()){
            flash("message", "Formulário invalido!");
            return Temas.temas(BADREQUEST, idTema);
        }
        Tema tema = dao.findByEntityId(Tema.class, idTema);
        if (filledForm.get("conselho") != null) {
            try {
                String conselho = filledForm.get("conselho");
                tema.addDica(new Conselho(session().get("user"), tema, conselho));
                dao.merge(tema);
                dao.flush();

                Logger.debug("Adicionou dica");
                return Temas.temas(OK, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(BADREQUEST, idTema);
            }
        } else if (filledForm.get("url") != null){
            try {
                String url = filledForm.get("url");
                tema.addDica(new MaterialUtil(session().get("user"), tema, url));
                dao.merge(tema);
                dao.flush();

                Logger.debug("Adicionou dica");
                return Temas.temas(OK, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(BADREQUEST, idTema);
            }
        } else if (filledForm.get("disciplina") != null){
            try {
                String disciplina = filledForm.get("disciplina");
                String razao = filledForm.get("razao");
                tema.addDica(new DisciplinaUtil(session().get("user"), tema, disciplina, razao));
                dao.merge(tema);
                dao.flush();

                Logger.debug("Adicionou dica");
                return Temas.temas(OK, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(BADREQUEST, idTema);
            }
        } else if (filledForm.get("assunto") != null){
            try {
                String assunto = filledForm.get("assunto");
                tema.addDica(new PrecisaSaber(session().get("user"), tema, assunto));
                dao.merge(tema);
                dao.flush();

                Logger.debug("Adicionou dica");
                return Temas.temas(OK, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(BADREQUEST, idTema);
            }
        }
        flash("message", "Ocorreu um erro");
        return Temas.temas(BADREQUEST, idTema);
    }

    @Transactional
    public static Result addOpiniao(long idTema, long idDica, int opiniao) {
        DynamicForm filledForm = Form.form().bindFromRequest();
        if (filledForm.hasErrors()){
            flash("message", "Formulário invalido!");
            return Temas.temas(BADREQUEST, idTema);
        }
        DicaGenerica dica = dao.findByEntityId(DicaGenerica.class, idDica);
        if (opiniao == 0){
            String justificativa = filledForm.get("justificativa");
            try {
                dica.addOpiniaoNegativa(session().get("user"), justificativa);
                dao.merge(dica);
                dao.flush();
                return Temas.temas(OK, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(BADREQUEST, idTema);
            }
        } else if (opiniao == 1){
            try {
                dica.addOpiniaoPositiva(session().get("user"));
                dao.merge(dica);
                dao.flush();
                return Temas.temas(OK, idTema);
            } catch (Exception e) {
                flash("message", e.getMessage());
                return Temas.temas(BADREQUEST, idTema);
            }
        } else {
            flash("message", "Erro: Não foi possível adicionar opinião!");
            return Temas.temas(BADREQUEST, idTema);
        }
    }

    @Transactional
    public static Result reportarConteudoInapropriado(long idTema, long idDica) {
        Tema tema = dao.findByEntityId(Tema.class, idTema);
        DicaGenerica dica = dao.findByEntityId(DicaGenerica.class, idDica);

        try {
            tema.informaConteudoInapropriado(dica, session().get("user"));
            return Temas.temas(OK, idTema);
        } catch (Exception e) {
            flash("message", e.getMessage());
            return Temas.temas(BADREQUEST, idTema);
        }
    }
}
