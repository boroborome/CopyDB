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
package com.happy3w.etl.copydb.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This Factory is used to initialize a DataTermination by customer input.
 * @author boroborome
 */
public class DataTerminationFactory {
    private static Map<String, Class<? extends AbstractDataTermination>> criteriaTypeMap = new HashMap<>();

    public static void register(String typeName, Class<? extends AbstractDataTermination> type) {
        criteriaTypeMap.put(typeName, type);
    }

    public static Class<? extends AbstractDataTermination> getType(String typeName) {
        return criteriaTypeMap.get(typeName);
    }

    public static Collection<Class<? extends AbstractDataTermination>> getAllTypes() {
        return criteriaTypeMap.values();
    }

    public static AbstractDataTermination newInstanceByType(String typeName) throws IllegalAccessException, InstantiationException {
        Class<? extends AbstractDataTermination> type = getType(typeName);
        return type == null ? null : type.newInstance();
    }

    public static AbstractDataTermination newInstanceByTerminationStr(
            String terminationString, IConfigSuppler configSuppler) throws InstantiationException, IllegalAccessException {
        int pos = terminationString.indexOf(':');
        String typeCode = terminationString.substring(0, pos);
        String terminationStr = terminationString.substring(pos + 1);

        AbstractDataTermination termination = newInstanceByType(typeCode);
        termination.initializeByString(terminationStr, configSuppler);
        return termination;
    }
}
