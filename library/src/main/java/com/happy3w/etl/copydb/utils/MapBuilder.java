/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.happy3w.etl.copydb.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Builder to build a map.
 * @author boroborome
 */
public class MapBuilder<K, V> {
    private Map<K, V> map = new HashMap<>();

    /**
     * Constructor that also enters the first entry.
     * @param key the first key
     * @param value the first value
     */
    private MapBuilder(K key, V value) {
        and(key, value);
    }

    /**
     * Factory method that creates the builder and enters the first entry.
     * @param key key to add in map
     * @param value value to add in map
     * @param <A> key type
     * @param <B> value type
     * @return The MapBuilder
     */
    public static <A, B> MapBuilder<A, B> of(A key, B value) {
        return new MapBuilder<>(key, value);
    }

    /**
     * Puts the key-value pair to the map and returns itself for method chaining
     * @param key key to add in map
     * @param value value to add in map
     * @return This MapBuilder
     */
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

    /**
     * Return an mutable map
     * @return contents of MapBuilder as an modifiable map.
     */
    public Map<K, V> buildNormal() {
        return map;
    }
}