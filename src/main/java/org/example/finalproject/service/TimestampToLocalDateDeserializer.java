package org.example.finalproject.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class TimestampToLocalDateDeserializer extends StdDeserializer<LocalDate> {

    public TimestampToLocalDateDeserializer() {
        this(null);
    }

    protected TimestampToLocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        if (p.currentToken().isNumeric()) {
            long timestamp = p.getLongValue();
            return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        }
        throw new IllegalArgumentException("Expected numeric timestamp");
    }
}
