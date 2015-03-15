package controllers;

import models.Usuario;
import models.dao.GenericDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import views.html.login;

import java.util.List;

import static play.mvc.Controller.session;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by orion on 15/03/15.
 */
public class Login {

    private static GenericDAO dao = new GenericDAO();
    private static Form<Usuario> form = Form.form(Usuario.class);

    @Transactional
    public static Result login(){
        Form<Usuario> filledForm = form.bindFromRequest();
        Usuario user = filledForm.get();

        if (filledForm.hasErrors() || !verificaAutenticacao(user)) {
            return badRequest();
        } else {
            session().clear();
            session("user", filledForm.get().getUser());
            return redirect(routes.Application.index());
        }
    }

    public static Result show(){
        return ok(login.render("algo"));
    }

    private static boolean verificaAutenticacao(Usuario user){
        List<Usuario> users = dao.findAllByClass(Usuario.class);
        for (Usuario u: users){
            if (u.getUser().equals(user.getUser()) && u.autentica(user.getUser(), user.getPassword())){
                Logger.debug("Se logou!");
                return true;
            }
        }
        return false;
    }
}
