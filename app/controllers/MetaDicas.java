package controllers;

import models.DicaGenerica;
import models.MetaDica;
import models.dao.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import views.html.metadicas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static play.mvc.Controller.flash;
import static play.mvc.Controller.session;
import static play.mvc.Results.ok;

/**
 * Created by orion on 21/03/15.
 */
public class MetaDicas {

    private static GenericDAO dao = new GenericDAO();

    @Transactional
    public static Result addMetaDica() {
        DynamicForm filledForm = Form.form().bindFromRequest();
        if (filledForm.hasErrors()){
            flash("message", "Formulário invalido!");
            show();
        }
        String comentario = filledForm.get("comentario");
        Map<String, String> form = filledForm.data();
        List<DicaGenerica> dicas = new ArrayList<DicaGenerica>();

        for (int i = 0; i < form.size() - 1; i++){
            dicas.add(dao.findByEntityId(DicaGenerica.class, Long.parseLong(form.get("dica["+i+"]"))));
        }

        try {
            DicaGenerica metaDica = new MetaDica(session().get("user"), comentario, dicas);
            dao.persist(metaDica);
            return show();
        } catch (Exception e) {
            flash("message", e.getMessage());
            return show();
        }
    }

    @Transactional
    public static Result addOpiniao(long idMeta, int opiniao) {
        DynamicForm filledForm = Form.form().bindFromRequest();
        if (filledForm.hasErrors()){
            flash("message", "Formulário invalido!");
            return show();
        }
        DicaGenerica dica = dao.findByEntityId(DicaGenerica.class, idMeta);
        if (opiniao == 0){
            String justificativa = filledForm.get("justificativa");
            try {
                dica.addOpiniaoNegativa(session().get("user"), justificativa);
                dao.merge(dica);
                dao.flush();
                return show();
            } catch (Exception e) {
                flash("message", e.getMessage());
                return show();
            }
        } else if (opiniao == 1){
            try {
                dica.addOpiniaoPositiva(session().get("user"));
                dao.merge(dica);
                dao.flush();
                return show();
            } catch (Exception e) {
                flash("message", e.getMessage());
                return show();
            }
        } else {
            flash("message", "Erro: Não foi possível adicionar opinião!");
            return show();
        }
    }

    @Transactional
    public static Result show() {
        String message = flash("message");
        List<DicaGenerica> dicas = dao.findAllByClass(DicaGenerica.class);
        List<MetaDica> metaDicas = new ArrayList<MetaDica>();
        for (DicaGenerica dica: dicas){
            if (dica instanceof MetaDica){
                metaDicas.add((MetaDica)dica);
            }
        }
        return ok(metadicas.render(message, dicas, metaDicas));
    }
}
