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

/**
 * Utility used in primitive
 * @author boroborome
 */
public class PrimitiveUtil {
    public static String dropPointValue(String value) {
        int pos = value.indexOf('.');
        return pos < 0 ? value : value.substring(0, pos);
    }
}
