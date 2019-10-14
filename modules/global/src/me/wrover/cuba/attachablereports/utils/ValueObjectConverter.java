package me.wrover.cuba.attachablereports.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Strings;
import com.haulmont.cuba.core.entity.EmbeddableEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class ValueObjectConverter implements AttributeConverter<EmbeddableEntity, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    private static final String SEPARATOR = ":";

    @Override
    public String convertToDatabaseColumn(EmbeddableEntity valueObject) {

        if (valueObject == null)
            return "";

        try {
            return valueObject.getMetaClass().getName() + SEPARATOR +
                    mapper.writeValueAsString(valueObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("error marshalling a value object to JSON", e);
        }
    }

    @Override
    public EmbeddableEntity convertToEntityAttribute(String value) {

        if (Strings.isNullOrEmpty(value))
            return null;

        final int separatorIndex = value.indexOf(SEPARATOR);

        if (separatorIndex == -1)
            throw new IllegalArgumentException("wrong data passed to VO unmarshalling (" + value + ")");

        Class clazz = AppBeans.get(Metadata.class).getClassNN(value.substring(0, separatorIndex)).getJavaClass();

        try {
            //noinspection unchecked .getJavaClass() returns raw type
            return (EmbeddableEntity) mapper.readValue(value.substring(separatorIndex + 1), clazz);
        } catch (IOException e) {
            throw new RuntimeException("error unmarshalling a value object to JSON", e);
        }
    }
}
