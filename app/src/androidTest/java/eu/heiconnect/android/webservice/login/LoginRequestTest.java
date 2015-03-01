package eu.heiconnect.android.webservice.login;

import com.android.volley.AuthFailureError;

import junit.framework.TestCase;

public class LoginRequestTest extends TestCase {

    public void testRequest() throws AuthFailureError {
        // Given
        User user = new User("h09330", "pass");
        LoginRequest request = new LoginRequest(user);

        // When
        String requestBodyAsString = new String(request.getBody());

        // Then
        String expected = "{\"user\":{\"password\":\"pass\",\"ecampus_id\":\"h09330\"}}";
        assertEquals(expected, requestBodyAsString);
    }

}