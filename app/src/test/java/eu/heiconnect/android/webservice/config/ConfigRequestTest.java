package eu.heiconnect.android.webservice.config;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.webservice.AbstractDeserializationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ConfigRequestTest extends AbstractDeserializationTest {

    @Test
    public void testDeserialization() throws IOException {
        // Given
        InputStream raw = inputStream.get("config.json");

        // When
        ConfigResult result = mapper.readValue(raw, ConfigResult.class);

        // Then
        assertNotNull(result);
        assertNotNull(result.getConfig());
        assertEquals("http://10.0.3.2:3000/", result.getConfig().getUrl());
        assertEquals(1000, result.getConfig().getAndroidMinimumVersion());
    }

}
