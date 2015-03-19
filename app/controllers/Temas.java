package controllers;

import models.Tema;
import models.dao.GenericDAO;
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

    @Transactional
    public static Result show() {
        return temas(200, new Long(-1));
    }

    @Transactional
    public static Result temas(Long id){
        return temas(200, id);
    }

    @Transactional
    public static Result temas(int status){
        return temas(status, new Long(-1));
    }

    @Transactional
    public static Result temas(int status, Long id){
        if (session().get("user") != null) {
            List<Tema> listaDeTemas = dao.findAllByClass(Tema.class);

            String message = flash("message");
            if (message == null) {
                message = "";
            }

            if (id.equals(new Long(-1)) && listaDeTemas.size() > 0) {
                id = listaDeTemas.get(0).getId();
            }

            switch (status) {
                case 200:
                    return ok(temas.render(message, id, listaDeTemas));
                case 400:
                    return badRequest(temas.render(message, id, listaDeTemas));
                default:
                    return badRequest(temas.render(message, id, listaDeTemas));
            }
        } else {
            return badRequest(index.render("Erro: Você não está logado!"));
        }
    }
}
