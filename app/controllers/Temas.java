package controllers;

import models.Tema;
import models.dao.GenericDAO;
import play.db.jpa.Transactional;
import play.mvc.Result;
import views.html.temas;

import java.util.List;

import static play.mvc.Controller.flash;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by M on 18/03/2015.
 */
public class Temas {

    private static GenericDAO dao = new GenericDAO();

    @Transactional
    public static Result show() {
        return show(200);
    }


    @Transactional
    public static Result show(int status){
        String message = flash("message");
        if(message == null) {
            message = "";
        }
        switch (status){
            case 200:
                return ok(temas.render(message, temas()));
            case 400:
                return badRequest(temas.render(message,temas()));
            default:
                return badRequest(temas.render(message,temas()));
        }
    }

    public static List<Tema> temas(){
        List<Tema> listaDeTemas = dao.findAllByClass(Tema.class);
        return  listaDeTemas;
    }

}
