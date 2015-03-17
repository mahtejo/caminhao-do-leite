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

import java.util.List;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Created by orion on 14/03/15.
 */
public class Cadastro {

    private static GenericDAO dao = new GenericDAO();
    private static Form<Usuario> form = Form.form(Usuario.class);

    @Transactional
    public static Result cadastrar() {
        DynamicForm filledForm = Form.form().bindFromRequest();
        String nome = filledForm.get("nome");
        String login = filledForm.get("user");
        String senha = filledForm.get("senha");

        Usuario user = new Usuario(nome, senha, login);

        if (filledForm.hasErrors() || !isValido(user)) {
            return badRequest(cadastro.render("Não foi possível se cadastrar!"));
        } else {
            Logger.debug("Criando usuário: " + filledForm.data().toString() + " como " + user.getUser());

            dao.persist(user);
            dao.flush();
            return ok(index.render("Cadastro concluído com sucesso!"));
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
        // deve mandar como parâmetro algo mais significativo
        return ok(cadastro.render("Tela cadastro"));
    }
}
