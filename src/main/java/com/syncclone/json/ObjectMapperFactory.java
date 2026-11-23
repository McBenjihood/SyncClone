package com.syncclone.json;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.*;
import tools.jackson.databind.cfg.EnumFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.time.Instant;

public class ObjectMapperFactory {

    private static final ObjectMapper INSTANCE = createInstance();

    public static ObjectMapper getInstance(){
        return INSTANCE;
    }

    private static ObjectMapper createInstance(){
        SimpleModule instantModule = new SimpleModule()
                .addSerializer(Instant.class, new ValueSerializer<>() {
                    @Override
                    public void serialize(
                            Instant value,
                            JsonGenerator generator,
                            SerializationContext context
                    ) throws JacksonException {
                        generator.writeNumber(value.getEpochSecond());
                    }
                })
                .addDeserializer(Instant.class, new ValueDeserializer<>() {
                    @Override
                    public Instant deserialize(
                            JsonParser parser,
                            DeserializationContext context
                    ) throws JacksonException {
                        return Instant.ofEpochSecond(parser.getLongValue());
                    }
                });

        return JsonMapper.builder()
                .addModule(instantModule)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                // Store enums as names, not indexes.
                .enable(EnumFeature.WRITE_ENUMS_USING_TO_STRING)

                .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                .build();
    }
}
