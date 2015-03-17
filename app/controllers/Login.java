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

import static play.mvc.Controller.session;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by orion on 15/03/15.
 */
public class Login {

    private static GenericDAO dao = new GenericDAO();
    private static Form<Login> form = Form.form(Login.class);
    private static boolean userValido;

    public String user;
    public String senha;

    @Transactional
    public static Result login() {
        DynamicForm filledForm = Form.form().bindFromRequest();
        String usuario = filledForm.get("user");
        String senha = filledForm.get("senha");


        if (filledForm.hasErrors() || !verificaAutenticacao(usuario, senha)) {
            Logger.debug("Deu bad request com o usuário " + usuario + " e senha " + senha);
            return badRequest("Não foi possível efetuar o login!");
        } else {
            session().clear();
            session("user", usuario);
            return ok("Se logou!");
        }
    }

    @Transactional
    public static Result logout() {
        session().clear();
        return show();
    }

    @Transactional
    public static Result show() {
        if (session().get("user") == null) {
            return ok(login.render("Tela login"));
        } //não precisa desse tratamento pq qd um user se logar, a página 'login' não estará mais disponivel pra ele
        return ok(index.render("Você já está logado!"));
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

    public static boolean getUserValido() {
        if (userValido) {
            return true;
        } else {
            return false;
        }
    }
}