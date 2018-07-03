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

import org.springframework.util.StringUtils;

/**
 * This Converter is used to convert between Long and String.
 * @author boroborome
 */
public class LongConverter implements IPrimitiveConverter<Long> {
    @Override
    public Class<Long> dataType() {
        return Long.class;
    }
    @Override
    public Class primitiveType() {
        return Long.TYPE;
    }
    @Override
    public String convertToString(Long value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Long convertFromString(String str) {
        return StringUtils.isEmpty(str) ? null : Long.parseLong(PrimitiveUtil.dropPointValue(str));
    }

}
