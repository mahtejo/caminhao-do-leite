import base.AbstractTest;
import models.Usuario;
import models.dao.GenericDAO;
import org.junit.Test;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.callAction;
import static play.test.Helpers.fakeRequest;

/**
 * Created by orion on 18/03/15.
 */
public class CadastroTest extends AbstractTest {

    GenericDAO dao = new GenericDAO();
    List<Usuario> users = new ArrayList<Usuario>();

    @Test
    public void deveSeCadastrar() {
        Map<String, String> formulario = new HashMap<String, String>();
        formulario.put("nome", "joao bonifácio");
        formulario.put("senha", "123456");
        formulario.put("user", "joao");
        Result result = callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        users = dao.findAllByClass(Usuario.class);

        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void naoDeveCadastrarMesmoUsuario(){
        Map<String, String> formulario = new HashMap<String, String>();
        formulario.put("nome", "joao bonifácio");
        formulario.put("senha", "123456");
        formulario.put("user", "joao");

        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        formulario.put("nome", "maria josefina");
        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        users = dao.findAllByClass(Usuario.class);
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void naoDeveCadastrarMesmoNome(){
        Map<String, String> formulario = new HashMap<String, String>();
        formulario.put("nome", "joao bonifácio");
        formulario.put("senha", "123456");
        formulario.put("user", "joao");

        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        formulario.put("user", "maria");
        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        users = dao.findAllByClass(Usuario.class);
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void deveCadastrarMaisDeUmUsuario(){
        Map<String, String> formulario = new HashMap<String, String>();
        formulario.put("nome", "joao bonifácio");
        formulario.put("senha", "123456");
        formulario.put("user", "joao");

        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        formulario.put("user", "maria");
        formulario.put("nome", "maria josefina");
        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        users = dao.findAllByClass(Usuario.class);
        assertThat(users.size()).isEqualTo(2);
    }
}
