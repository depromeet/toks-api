package com.tdns.toks.core.common.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class LongArrayConverter implements AttributeConverter<List<Long>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        return attribute.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(SPLIT_CHAR)).map(Long::valueOf).collect(Collectors.toList());
    }
}
