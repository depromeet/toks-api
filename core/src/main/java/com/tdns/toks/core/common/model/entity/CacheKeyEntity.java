package com.tdns.toks.core.common.model.entity;

import com.tdns.toks.core.common.type.CacheType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@Setter
public class CacheKeyEntity {
    public static final String SEPARATOR = "_";

    private CacheType type;
    private String valueKey;
    private String hashKey;
    private String hashField;

    private CacheKeyEntity(CacheType type) {
        this.type = type;
    }

    public static CacheKeyEntity valueKey(CacheType type, Object... args) {
        CacheKeyEntity key = new CacheKeyEntity(type);
        key.setType(type);
        key.setValueKey(generateValueKey(type, args));
        return key;
    }

    private static String generateValueKey(CacheType type, Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("k:").append(type.getName());

        if (args.length > 0) {
            sb.append(":").append(StringUtils.join(args, SEPARATOR));
        }

        return sb.toString();
    }

    public static CacheKeyEntity hashKey(CacheType type, Object... args) {
        CacheKeyEntity key = new CacheKeyEntity(type);
        key.setType(type);
        key.setHashKey(generateHashKey(type, args));
        return key;
    }

    private static String generateHashKey(CacheType type, Object... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("hk:").append(type.getName());

        if (args.length > 0) {
            sb.append(":").append(StringUtils.join(args, SEPARATOR));
        }

        return sb.toString();
    }

    public CacheKeyEntity hashField(Object... args) {
        this.hashField = String.format("hf:%s", StringUtils.join(args, SEPARATOR));
        return this;
    }

    public static CacheKeyEntity make(String hashKey, String cacheKey) {
        boolean isHashOps = StringUtils.isNotEmpty(hashKey);
        String typeString = "";
        if (isHashOps) {
            typeString = hashKey.split(":")[1];
        } else {
            typeString = cacheKey.split(":")[1];
        }

        final String typeStr = typeString;
        CacheType cacheType = Arrays.stream(CacheType.values())
            .filter(type -> type.getName().equals(typeStr))
            .findFirst()
            .orElseGet(() -> null);

        CacheKeyEntity key = new CacheKeyEntity(cacheType);

        if (isHashOps) {
            key.setHashKey(hashKey);
        } else {
            key.setValueKey(cacheKey);
        }

        return key;
    }
}
