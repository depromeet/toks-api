package com.tdns.toks.core.common.model.converter;

import com.tdns.toks.core.common.utils.MapperUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;

@Converter
public class MapConverter implements AttributeConverter<Map<String, Object>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        return MapperUtil.writeValueAsString(attribute);
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        return MapperUtil.readValue(dbData, Map.class);
    }
}
