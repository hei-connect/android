package eu.heiconnect.android.webservice;

import android.test.InstrumentationTestCase;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class AbstractDeserializationTest extends InstrumentationTestCase {

    protected ObjectMapper mapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    }
}
