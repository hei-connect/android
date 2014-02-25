package eu.heiconnect.android.webservice;

import com.android.volley.AuthFailureError;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.model.LoginResult;
import eu.heiconnect.android.model.User;
import eu.heiconnect.android.test.R;


public class LoginRequestTest extends AbstractDeserializationTest {

    public void testDeserialization() throws IOException {
        // Given
        InputStream raw = getInstrumentation().getContext().getResources().openRawResource(R.raw.login);

        // When
        LoginResult result = mapper.readValue(raw, LoginResult.class);

        // Then
        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals("h09330", result.getUser().getEcampusId());
        assertEquals("ze_fake_token_666_of_ze_death", result.getUser().getApiToken());
        assertNull(result.getUser().getPassword());
    }

    public void testRequest() throws AuthFailureError {
        // Given
        User user = new User("h09330", "pass");
        LoginRequest request = new LoginRequest(user, null, null);

        // When
        String requestBodyAsString = new String(request.getBody());

        // Then
        String expected = "{\"user\":{\"password\":\"pass\",\"api_token\":null,\"ecampus_id\":\"h09330\"}}";
        assertEquals(expected, requestBodyAsString);
    }
}
