package eu.heiconnect.android.webservice.login;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.webservice.AbstractDeserializationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class LoginRequestTest extends AbstractDeserializationTest {

    @Test
    public void testDeserialization() throws IOException {
        // Given
        InputStream raw = inputStream.get("login.json");

        // When
        LoginResult result = mapper.readValue(raw, LoginResult.class);

        // Then
        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals("h09330", result.getUser().getEcampusId());
        assertEquals("ze_fake_token_666_of_ze_death", result.getUser().getApiToken());
        assertNull(result.getUser().getPassword());
    }
}
