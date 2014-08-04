package eu.heiconnect.android.webservice;

import android.test.InstrumentationTestCase;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractDeserializationTest extends InstrumentationTestCase {

    protected ObjectMapper mapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mapper = BaseRequest.getAndInitializeMapper();
    }
}
