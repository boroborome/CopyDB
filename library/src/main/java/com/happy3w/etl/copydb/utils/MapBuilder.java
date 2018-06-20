package com.happy3w.etl.copydb.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
    private Map<K, V> map = new HashMap<>();

    /** Constructor that also enters the first entry. */
    private MapBuilder(K key, V value) {
        and(key, value);
    }

    /** Factory method that creates the builder and enters the first entry. */
    public static <A, B> MapBuilder<A, B> of(A key, B value) {
        return new MapBuilder<>(key, value);
    }

    /** Puts the key-value pair to the map and returns itself for method chaining */
    public MapBuilder<K, V> and(K key, V value) {
        map.put(key, value);
        return this;
    }

    /**
     * If no reference to builder is kept and both the key and value types are immutable,
     * the resulting map is immutable.
     * @return contents of MapBuilder as an unmodifiable map.
     */
    public Map<K, V> build() {
        return Collections.unmodifiableMap(map);
    }

    public Map<K, V> buildNormal() {
        return map;
    }
}