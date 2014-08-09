package eu.heiconnect.android.webservice;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.test.R;
import eu.heiconnect.android.webservice.absences.AbsencesResult;


public class AbsencesRequestTest extends AbstractDeserializationTest {

    public void testDeserialization() throws IOException {
        // Given
        InputStream raw = getInstrumentation().getContext().getResources().openRawResource(R.raw.absences);

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
