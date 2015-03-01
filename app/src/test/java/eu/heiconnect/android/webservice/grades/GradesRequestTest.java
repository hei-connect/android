package eu.heiconnect.android.webservice.grades;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.webservice.AbstractDeserializationTest;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class GradesRequestTest extends AbstractDeserializationTest {

    @Test
    public void testDeserialization() throws IOException {
        // Given
        InputStream raw = inputStream.get("grades.json");

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
        assertEquals(16.0, result.getGrades().get(0).getMark(), 0);
        assertEquals(12.5, result.getGrades().get(0).getAverage(), 0);
        assertEquals(2, result.getGrades().get(0).getAverageCount());
        assertNotNull(result.getLastUpdate());
    }

}
