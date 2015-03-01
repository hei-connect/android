package eu.heiconnect.android.webservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;

import eu.heiconnect.android.RawFileInputStream;

public abstract class AbstractDeserializationTest {

    protected RawFileInputStream inputStream;
    protected ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        inputStream = new RawFileInputStream();
        mapper = BaseRequest.getAndInitializeMapper();
    }
}
