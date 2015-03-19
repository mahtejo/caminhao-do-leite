package controllers;

import models.Usuario;
import models.dao.GenericDAO;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import views.html.index;
import views.html.login;

import java.util.List;

import static play.mvc.Controller.flash;
import static play.mvc.Controller.session;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by orion on 15/03/15.
 */
public class Login {

    private static GenericDAO dao = new GenericDAO();

    public String user;
    public String senha;

    @Transactional
    public static Result login() {
        DynamicForm filledForm = Form.form().bindFromRequest();
        String usuario = filledForm.get("user");
        String senha = filledForm.get("senha");

        if (filledForm.hasErrors() || !verificaAutenticacao(usuario, senha)) {
            Logger.debug("Deu bad request com o usuário " + usuario + " e senha " + senha);
            return badRequest(login.render("Erro: Usuário ou senha inválidos!"));
        } else {
            session().clear();
            session("user", usuario);
            flash("message", "Login efetuado com sucesso!");
            return Temas.show(200);
        }
    }

    @Transactional
    public static Result logout() {
        session().clear();
        if (session().get("user") == null) {
            return ok(index.render(""));
        } else {
            flash("message", "Erro: Tente fazer logout novamente!");
            return Temas.show(400);
        }
    }

    @Transactional
    public static Result show() {
        if (session().get("user") != null) {
            //return badRequest(temas.render("Erro: Você já está logado!", Temas.temas()));
            flash("message", "Erro: Você já está logado!");
            return Temas.show(400);
        }
        return ok(login.render(""));
    }

    private static boolean verificaAutenticacao(String username, String password) {
        List<Usuario> users = dao.findAllByClass(Usuario.class);
        if (users.isEmpty()) {
            return false;
        } else {
            for (Usuario u : users) {
                if (u.getUser().equals(username) && u.autentica(username, password)) {
                    Logger.debug("Login efetuado com sucesso!");
                    return true;
                }
            }
        }
        return false;
    }
}