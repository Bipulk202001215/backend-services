package com.example.common.id;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
public class MongoGeneratedIdListener extends AbstractMongoEventListener<Object> {

    private final CustomIdGenerator customIdGenerator;

    public MongoGeneratedIdListener(CustomIdGenerator customIdGenerator) {
        this.customIdGenerator = customIdGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        if (source == null) {
            return;
        }

        ReflectionUtils.doWithFields(source.getClass(), field -> setGeneratedId(source, field),
                field -> field.isAnnotationPresent(GeneratedId.class));
    }

    private void setGeneratedId(Object source, Field field) {
        ReflectionUtils.makeAccessible(field);

        Object value = ReflectionUtils.getField(field, source);
        if (value instanceof String stringValue && StringUtils.hasText(stringValue)) {
            return;
        }
        if (value != null) {
            return;
        }

        GeneratedId annotation = field.getAnnotation(GeneratedId.class);
        String generatedValue = customIdGenerator.generate(annotation.prefix(), annotation.separator(), annotation.length());
        ReflectionUtils.setField(field, source, generatedValue);
    }
}

