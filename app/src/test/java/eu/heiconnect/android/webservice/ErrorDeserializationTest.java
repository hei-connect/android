package eu.heiconnect.android.webservice;

import com.fasterxml.jackson.databind.DeserializationFeature;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ErrorDeserializationTest extends AbstractDeserializationTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    @Test
    public void testDeserialization1() throws IOException {
        // Given
        InputStream raw = inputStream.get("error_login_1.json");

        // When
        Error error = mapper.readValue(raw, Error.class);

        // Then
        assertNotNull(error);
        assertEquals(20, error.getCode());
        assertEquals("Login/password incorrect", error.getMessage());
    }

    @Test
    public void testDeserialization2() throws IOException {
        // Given
        InputStream raw = inputStream.get("error_login_2.json");

        // When
        Error error = mapper.readValue(raw, Error.class);

        // Then
        assertNotNull(error);
        assertEquals(21, error.getCode());
        assertEquals("Compte inconnu, inscris toi sur hei-connect.eu", error.getMessage());
    }
}
