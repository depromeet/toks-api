package com.tdns.toks.core.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlConvertUtil {
    private static String urlPrefix;

    private static String studyPath;

    private static String joinPath;

    @Value("${url.prefix}")
    public void setUrlPrefix(String prefix) {
        urlPrefix = prefix;
    }

    @Value("${url.study-path}")
    public void setStudyPath(String path) {
        studyPath = path;
    }

    @Value("${url.join-path}")
    public void setJoinPath(String path) {
        joinPath = path;
    }

    public static String convertToInviteUrl(Long studyId){
        //TODO front url 확인 필요 -> 임시 테스트 url 적용
        return urlPrefix + studyPath + "/" + studyId + joinPath;
    }
}
