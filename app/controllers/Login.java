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

    public String user;
    public String password;

    @Transactional
    public static Result login(){
        DynamicForm filledForm = Form.form().bindFromRequest();
        String login = filledForm.get("user");
        String senha = filledForm.get("senha");


        if (filledForm.hasErrors() || !verificaAutenticacao(login, senha)) {
            Logger.debug("Deu bad request com o usuário " + login + " e senha " + senha);
            return badRequest();
        } else {
            session().clear();
            session("user", login);
            return ok(index.render("Se logou!"));
        }
    }

    public static Result show(){
        // deve mandar como parâmetro algo mais significativo
        return ok(login.render("Tela login"));
    }

    private static boolean verificaAutenticacao(String username, String password){
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
