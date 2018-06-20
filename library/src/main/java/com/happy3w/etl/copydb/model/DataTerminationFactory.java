package com.happy3w.etl.copydb.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
