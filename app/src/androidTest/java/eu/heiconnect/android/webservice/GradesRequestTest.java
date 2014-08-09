package eu.heiconnect.android.webservice;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.test.R;
import eu.heiconnect.android.webservice.grades.GradesResult;


public class GradesRequestTest extends AbstractDeserializationTest {

    public void testDeserialization() throws IOException {
        // Given
        InputStream raw = getInstrumentation().getContext().getResources().openRawResource(R.raw.grades);

        // When
        GradesResult result = mapper.readValue(raw, GradesResult.class);

        // Then
        assertNotNull(result);
        assertNotNull(result.getGrades());
        assertEquals(2, result.getGrades().size());
        assertNotNull(result.getGrades().get(0));
        assertEquals("Sport associatif", result.getGrades().get(0).getSectionName());
        assertEquals("Sport 75%", result.getGrades().get(0).getExamName());
        assertNotNull(result.getGrades().get(0).getDate());
        assertFalse(result.getGrades().get(0).isUnknown());
        assertEquals(16.0, result.getGrades().get(0).getMark());
        assertEquals(12.5, result.getGrades().get(0).getAverage());
        assertEquals(2, result.getGrades().get(0).getAverageCount());
        assertNotNull(result.getLastUpdate());
    }

}
