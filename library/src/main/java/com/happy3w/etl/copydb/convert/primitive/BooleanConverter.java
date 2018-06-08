package com.happy3w.etl.copydb.convert.primitive;

import org.springframework.util.StringUtils;

public class BooleanConverter implements IPrimitiveConverter<Boolean> {
    @Override
    public Class<Boolean> dataType() {
        return Boolean.class;
    }

    @Override
    public Class primitiveType() {
        return Boolean.TYPE;
    }

    @Override
    public String convertToString(Boolean value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Boolean convertFromString(String str) {
        return StringUtils.isEmpty(str) ? null : Boolean.parseBoolean(str);
    }
}
