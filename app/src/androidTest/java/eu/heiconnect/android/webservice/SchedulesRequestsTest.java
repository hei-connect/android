package eu.heiconnect.android.webservice;

import java.io.IOException;
import java.io.InputStream;

import eu.heiconnect.android.test.R;
import eu.heiconnect.android.webservice.schedule.ScheduleResult;


public class SchedulesRequestsTest extends AbstractDeserializationTest {

    public void testDeserialization() throws IOException {
        // Given
        InputStream raw = getInstrumentation().getContext().getResources().openRawResource(R.raw.schedule);

        // When
        ScheduleResult result = mapper.readValue(raw, ScheduleResult.class);

        // Then
        assertNotNull(result);
        assertNotNull(result.getCourses());
        assertEquals(3, result.getCourses().size());
        assertEquals("Cours", result.getCourses().get(0).getKind());
        assertEquals("Management de projets", result.getCourses().get(0).getName());
        assertEquals("H207", result.getCourses().get(0).getPlace());
        assertNotNull(result.getCourses().get(0).getDate());
        assertNotNull(result.getCourses().get(0).getEndDate());
        assertNotNull(result.getLastUpdate());
        assertEquals("ok", result.getLastUpdate().getState());
        assertNotNull(result.getLastUpdate().getUpdatedAt());
    }

}
