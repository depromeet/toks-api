package com.tdns.toks.core.common.utils;

import com.tdns.toks.core.common.exception.ApplicationErrorType;
import com.tdns.toks.core.common.exception.SilentApplicationErrorException;

import java.util.ArrayList;
import java.util.List;

public class ArrayConvertUtil {

    //","로 구분되어있는 String을 List로 변환합니다.
    public static List<String> convertToList(String s) {
        try {
            String[] split = s.split(",");
            return new ArrayList<>(List.of(split));
        } catch (Exception e) {
            throw new SilentApplicationErrorException(ApplicationErrorType.CONVERT_STRING_ERROR);
        }
    }

    public static String convertToString(List<String> arr) {
        try {
            return String.join(",", arr);
        } catch (Exception e) {
            throw new SilentApplicationErrorException(ApplicationErrorType.CONVERT_ARRAY_ERROR);
        }
    }
}
