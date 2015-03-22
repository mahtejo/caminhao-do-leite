package controllers;

import models.Tema;
import models.dao.GenericDAO;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import views.html.index;
import views.html.temas;

import java.util.List;

import static play.mvc.Controller.flash;
import static play.mvc.Controller.session;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by M on 18/03/2015.
 */
public class Temas {

    private static GenericDAO dao = new GenericDAO();
    private static final int BADREQUEST = 400;
    private static final int OK = 200;
    private static final int IDINVALIDO = -1;
    private static final int PRIMEIROELEMENTO = 0;

    @Transactional
    public static Result show() {
        return temas(OK, new Long(IDINVALIDO));
    }

    @Transactional
    public static Result temas(Long id){
        return temas(OK, id);
    }

    @Transactional
    public static Result temas(int status){
        return temas(status, new Long(IDINVALIDO));
    }

    @Transactional
    public static Result temas(int status, Long id){
        if (session().get("user") != null) {
            List<Tema> listaDeTemas = dao.findAllByClass(Tema.class);

            String message = flash("message");
            if (message == null) {
                message = "";
            }

            if (id.equals(new Long(IDINVALIDO)) && listaDeTemas.size() > 0) {
                id = listaDeTemas.get(PRIMEIROELEMENTO).getId();
            }

            switch (status) {
                case OK:
                    return ok(temas.render(message, id, listaDeTemas));
                case BADREQUEST:
                    return badRequest(temas.render(message, id, listaDeTemas));
                default:
                    return badRequest(temas.render(message, id, listaDeTemas));
            }
        } else {
            return badRequest(index.render("Erro: Você não está logado!"));
        }
    }

    @Transactional
    public static Result addDificuldade(Long idTema) {
        DynamicForm filledForm = Form.form().bindFromRequest();
        String dificul = filledForm.get("dificuldade");
        int dificuldade;
        if (filledForm.hasErrors()){
            flash("message", "Formulário invalido!");
            return Temas.temas(BADREQUEST, idTema);
        }
        try{
            dificuldade = Integer.parseInt(dificul);
            Logger.debug("Dificuldade: " + dificuldade);
            Logger.debug("Usuário: " + session().get("user"));
        } catch (Exception e){
            flash("message", "Dificuldade deve ser um número inteiro!");
            return Temas.temas(BADREQUEST, idTema);
        }
        try{
            Tema tema = dao.findByEntityId(Tema.class, idTema);
            tema.addDificuldade(session().get("user"), dificuldade);
            dao.merge(tema);
            dao.flush();
            return Temas.temas(OK, idTema);
        } catch (Exception e){
            flash("message", e.getMessage());
            return Temas.temas(BADREQUEST, idTema);
        }
    }
}
