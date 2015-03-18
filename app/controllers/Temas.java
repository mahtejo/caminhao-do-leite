package controllers;

import play.mvc.Result;
import views.html.temas;

import static play.mvc.Results.ok;

/**
 * Created by M on 18/03/2015.
 */
public class Temas {

    public static Result show() {
        return ok(temas.render(""));
    }

}
