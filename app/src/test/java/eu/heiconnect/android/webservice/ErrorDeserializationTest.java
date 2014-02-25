package eu.heiconnect.android.webservice;

import com.fasterxml.jackson.databind.DeserializationFeature;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.model.Error;
import eu.heiconnect.android.test.R;


public class ErrorDeserializationTest extends AbstractDeserializationTest {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    public void testDeserialization1() throws IOException {
        // Given
        InputStream raw = getInstrumentation().getContext().getResources().openRawResource(R.raw.login_error_1);

        // When
        Error error = mapper.readValue(raw, Error.class);

        // Then
        assertNotNull(error);
        assertEquals("403.1", error.getCode());
        assertEquals("Login/password incorrect", error.getMessage());
    }

    public void testDeserialization2() throws IOException {
        // Given
        InputStream raw = getInstrumentation().getContext().getResources().openRawResource(R.raw.login_error_2);

        // When
        Error error = mapper.readValue(raw, Error.class);

        // Then
        assertNotNull(error);
        assertEquals("403.2", error.getCode());
        assertEquals("Compte inconnu, inscris toi sur hei-connect.eu", error.getMessage());
    }
}
