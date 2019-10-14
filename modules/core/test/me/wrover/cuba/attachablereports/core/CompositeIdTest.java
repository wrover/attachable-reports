package me.wrover.cuba.attachablereports.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import me.wrover.cuba.attachablereports.domain.example.CompositeId;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CompositeIdTest {

    private static final Logger log = LoggerFactory.getLogger(CompositeIdTest.class);

    @Test
    public void testSerializationAndEquality() throws IOException {
        CompositeId id = new CompositeId();
        id.setDate(new Date());
        id.setNumber(RandomStringUtils.randomAlphanumeric(5));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

        final String data = mapper.writeValueAsString(id);
        log.debug("{}", data);

        CompositeId phoenix = mapper.readValue(data, CompositeId.class);

        assertEquals(phoenix, id);
    }
}