package controllers;

import models.Usuario;
import models.dao.GenericDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;

import static play.data.Form.form;
import static play.mvc.Controller.flash;
import static play.mvc.Results.badRequest;
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

        if (filledForm.hasErrors() && validar(user)) {
            return badRequest();
        } else {
            Logger.debug("Criando usuário: " + filledForm.data().toString() + " como " + user.getUser());

            dao.persist(user);
            dao.flush();
            return redirect(routes.Application.index());
        }
    }

    private static boolean validar(Usuario user) {
        //deve testar se o e-mail é válido
        //deve testar se o e-mail já foi cadastrado
        //deve testar se o usuário já foi cadastrado
        return false;
    }
}
