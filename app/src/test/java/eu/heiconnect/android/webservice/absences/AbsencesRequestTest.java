package eu.heiconnect.android.webservice.absences;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.webservice.AbstractDeserializationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AbsencesRequestTest extends AbstractDeserializationTest {

    @Test
    public void testDeserialization() throws IOException {
        // Given
        InputStream raw = inputStream.get("absences.json");

        // When
        AbsencesResult result = mapper.readValue(raw, AbsencesResult.class);

        // Then
        assertNotNull(result);
        assertNotNull(result.getAbsences());
        assertNotNull(result.getAbsences().get(0));
        assertEquals("Electronique - Capteurs", result.getAbsences().get(0).getSectionName());
        assertNotNull(result.getAbsences().get(0).getDate());
        assertEquals(5400, result.getAbsences().get(0).getLength());
    }

}
