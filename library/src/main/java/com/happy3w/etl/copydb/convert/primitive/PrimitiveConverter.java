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
package com.happy3w.etl.copydb.convert.primitive;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * This Converter is IPrimiteriveConverter Manager
 * @author boroborome
 */
public class PrimitiveConverter {
    private static final PrimitiveConverter instance = new PrimitiveConverter();

    public static PrimitiveConverter getInstance() {
        return instance;
    }

    private Map<Class, IPrimitiveConverter> converterMap = new HashMap<>();
    public <T> T convert(Object source, Class<T> targetType) {
        IPrimitiveConverter targetConverter = converterMap.get(targetType);
        if (targetConverter == null) {
            throw new UnsupportedOperationException(
                    MessageFormat.format("Can''t convert {0} to {1} for no convert define for {1}.",
                            source, targetType));
        }

        String strSource = null;
        if (source != null) {
            IPrimitiveConverter sourceConverter = converterMap.get(source.getClass());
            if (sourceConverter == null) {
                throw new UnsupportedOperationException(
                        MessageFormat.format("Can''t convert {0} to {1} for no convert define for {2}.",
                                source, targetType, source.getClass()));
            }
            strSource = sourceConverter.convertToString(source);
        }
        return (T) targetConverter.convertFromString(strSource);
    }

    public void register(IPrimitiveConverter converter) {
        converterMap.put(converter.dataType(), converter);
        Class primitiveType = converter.primitiveType();
        if (primitiveType != null) {
            converterMap.put(primitiveType, converter);
        }
    }
}
