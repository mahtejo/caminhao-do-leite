package controllers;

import models.Tema;
import models.dao.GenericDAO;
import play.mvc.Result;
import views.html.temas;

import java.util.List;

import static play.mvc.Results.ok;

/**
 * Created by M on 18/03/2015.
 */
public class Temas {

    private static GenericDAO dao = new GenericDAO();

    public static Result show() {
        return ok(temas.render("", temas()));
    }

    public static List<Tema> temas(){
        List<Tema> listaDeTemas = dao.findAllByClass(Tema.class);
        return  listaDeTemas;
    }

}
