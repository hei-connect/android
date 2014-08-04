package eu.heiconnect.android.webservice;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.test.R;
import eu.heiconnect.android.webservice.config.ConfigResult;


public class ConfigRequestTest extends AbstractDeserializationTest {

    public void testDeserialization() throws IOException {
        // Given
        InputStream raw = getInstrumentation().getContext().getResources().openRawResource(R.raw.config);

        // When
        ConfigResult result = mapper.readValue(raw, ConfigResult.class);

        // Then
        assertNotNull(result);
        assertNotNull(result.getConfig());
        assertEquals("http://10.0.3.2:3000/", result.getConfig().getUrl());
        assertEquals(1000, result.getConfig().getAndroidMinimumVersion());
    }

}
