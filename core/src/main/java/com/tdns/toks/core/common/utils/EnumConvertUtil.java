package com.tdns.toks.core.common.utils;

import com.tdns.toks.core.common.model.entity.EnumModel;
import com.tdns.toks.core.common.model.entity.EnumValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumConvertUtil {
    public static List<EnumValue> convertToEnumValue(Class<? extends EnumModel> enumClass){
        return Arrays
                .stream(enumClass.getEnumConstants())
                .map(EnumValue::new)
                .collect(Collectors.toList());
    }
}
