import base.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static play.test.Helpers.*;

/**
 * Created by orion on 18/03/15.
 */
public class LoginTest extends AbstractTest {

    @Before
    public void before(){
        Map<String, String> formulario = new HashMap<String, String>();
        formulario.put("nome", "joao bonifácio");
        formulario.put("senha", "123456");
        formulario.put("user", "joao");

        callAction(controllers.routes.ref.Cadastro.cadastrar(), fakeRequest().withFormUrlEncodedBody(formulario));
    }

    @Test
    public void deveSeLogar(){
        Map<String, String> novoFormulario = new HashMap<String, String>();
        novoFormulario.put("user", "joao");
        novoFormulario.put("senha", "123456");
        Result result = callAction(controllers.routes.ref.Login.login(), fakeRequest().withFormUrlEncodedBody(novoFormulario));

        assertEquals(200, status(result));
        assertEquals("joao", session(result).get("user"));
    }

    @Test
    public void naoDeveSeLogarComSenhaErrada(){
        Map<String, String> formularioErrado = new HashMap<String, String>();
        formularioErrado.put("senha", "123a456");
        formularioErrado.put("user", "joao");
        Result result = callAction(controllers.routes.ref.Login.login(), fakeRequest().withFormUrlEncodedBody(formularioErrado));

        assertEquals(400, status(result));
        assertNotEquals("joao", session(result).get("user"));
    }

    @Test
    public void naoDeveSeLogarComUsuarioInvalido(){
        Map<String, String> formularioErrado = new HashMap<String, String>();
        formularioErrado.put("senha", "123456");
        formularioErrado.put("user", "maria");
        Result result = callAction(controllers.routes.ref.Login.login(), fakeRequest().withFormUrlEncodedBody(formularioErrado));

        assertEquals(400, status(result));
        assertNotEquals("maria", session(result).get("user"));
    }

    @Test
    public void deveFazerLogout(){
        Map<String, String> novoFormulario = new HashMap<String, String>();
        novoFormulario.put("nome", "joao bonifácio");
        novoFormulario.put("senha", "123456");
        callAction(controllers.routes.ref.Login.login(), fakeRequest().withFormUrlEncodedBody(novoFormulario));

        Result result = callAction(controllers.routes.ref.Login.logout(), fakeRequest());

        assertEquals(200, status(result));
        assertEquals(null, session(result).get("user"));
    }
}
