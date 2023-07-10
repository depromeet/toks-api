package com.tdns.toks.api.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cache<T> {
    private String key;
    private Class<T> type;
}
