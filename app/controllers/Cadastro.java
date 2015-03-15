package controllers;

import models.Usuario;
import models.dao.GenericDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import views.html.cadastro;

import java.util.List;

import static play.data.Form.form;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by orion on 14/03/15.
 */
public class Cadastro {

    private static GenericDAO dao = new GenericDAO();
    private static Form<Usuario> form = Form.form(Usuario.class);

    @Transactional
    public static Result cadastrar() {
        Form<Usuario> filledForm = form.bindFromRequest();
        Usuario user = filledForm.get();

        if (filledForm.hasErrors() || !isValido(user)) {
            return badRequest();
        } else {
            Logger.debug("Criando usuário: " + filledForm.data().toString() + " como " + user.getUser());

            dao.persist(user);
            dao.flush();
            return redirect(routes.Application.index());
        }
    }

    private static boolean isValido(Usuario user) {
        List<Usuario> users = dao.findAllByClass(Usuario.class);
        for (Usuario u: users){
            if (user.getUser().equals(u.getUser()) || user.getNome().equals(u.getNome())){
                return false;
            }
        }
        return true;
    }

    public static Result show() {
        // deve retornar algo mais significativo
        return ok(cadastro.render("algo"));
    }
}
