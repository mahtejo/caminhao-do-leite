package controllers;

import models.Usuario;
import models.dao.GenericDAO;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import views.html.cadastro;
import views.html.index;
import views.html.temas;

import java.util.List;

import static play.mvc.Controller.session;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by orion on 14/03/15.
 */
public class Cadastro {

    private static GenericDAO dao = new GenericDAO();

    @Transactional
    public static Result cadastrar() {
        DynamicForm filledForm = Form.form().bindFromRequest();
        String nome = filledForm.get("nome");
        String login = filledForm.get("user");
        String senha = filledForm.get("senha");

        Usuario user = new Usuario(nome, senha, login);

        if (filledForm.hasErrors() || !isValido(user)) {
            Logger.debug("Não foi possível cadastrar");
            return badRequest(cadastro.render("Seu cadastro não foi realizado com sucesso. Cheque os dados e tente novamente."));
        } else {
            Logger.debug("Criando usuário: " + filledForm.data().toString() + " como " + user.getUser());

            dao.persist(user);
            dao.flush();
            return ok(index.render("Seu cadastro foi realizado com sucesso!"));
        }
    }

    @Transactional
    public static Result show() {
        if (session().get("user") == null) {
            return ok(cadastro.render("Preencha os campos abaixo para se cadastrar."));
        } else {
            return badRequest(temas.render("Faça logout para se cadastrar!"));
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
}
