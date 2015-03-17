import base.AbstractTest;
import models.Usuario;
import models.dao.GenericDAO;
import org.junit.*;
import play.libs.F.*;
import play.mvc.*;
import play.test.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static play.test.Helpers.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends AbstractTest {

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

    @Test
    public void deveSeLogar(){
        Map<String, String> formulario = new HashMap<String, String>();
        formulario.put("nome", "joao bonifácio");
        formulario.put("senha", "123456");
        formulario.put("user", "joao");

        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));
        formulario.remove("nome");
        Result result = callAction(controllers.routes.ref.Login.login(), fakeRequest().withFormUrlEncodedBody(formulario));

        assertEquals(200, status(result));
        assertEquals("joao", session(result).get("user"));
    }

    @Test
    public void naoDeveSeLogarComSenhaErrada(){
        Map<String, String> formulario = new HashMap<String, String>();
        formulario.put("nome", "joao bonifácio");
        formulario.put("senha", "123456");
        formulario.put("user", "joao");
        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        Map<String, String> formularioErrado = new HashMap<String, String>();
        formularioErrado.put("senha", "123a456");
        formularioErrado.put("user", "joao");
        Result result = callAction(controllers.routes.ref.Login.login(), fakeRequest().withFormUrlEncodedBody(formularioErrado));

        assertEquals(400, status(result));
        assertNotEquals("joao", session(result).get("user"));
    }

    @Test
    public void naoDeveSeLogarComUsuarioInvalido(){
        Map<String, String> formulario = new HashMap<String, String>();
        formulario.put("nome", "joao bonifácio");
        formulario.put("senha", "123456");
        formulario.put("user", "joao");
        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        Map<String, String> formularioErrado = new HashMap<String, String>();
        formularioErrado.put("senha", "123456");
        formularioErrado.put("user", "maria");
        Result result = callAction(controllers.routes.ref.Login.login(), fakeRequest().withFormUrlEncodedBody(formularioErrado));

        assertEquals(400, status(result));
        assertNotEquals("maria", session(result).get("user"));
    }

    @Test
    public void deveFazerLogout(){
        Map<String, String> formulario = new HashMap<String, String>();
        formulario.put("nome", "joao bonifácio");
        formulario.put("senha", "123456");
        formulario.put("user", "joao");
        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));

        formulario.remove("nome");
        callAction(controllers.routes.ref.Login.login(), fakeRequest().withFormUrlEncodedBody(formulario));

        Result result = callAction(controllers.routes.ref.Login.logout(), fakeRequest().withFormUrlEncodedBody(formulario));

        assertEquals(200, status(result));
        assertEquals(null, session(result).get("user"));
    }
}
