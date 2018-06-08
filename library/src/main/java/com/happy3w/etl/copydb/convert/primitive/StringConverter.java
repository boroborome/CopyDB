package com.happy3w.etl.copydb.convert.primitive;

public class StringConverter implements IPrimitiveConverter<String> {
    @Override
    public Class<String> dataType() {
        return String.class;
    }
    @Override
    public Class primitiveType() {
        return null;
    }
    @Override
    public String convertToString(String value) {
        return value;
    }

    @Override
    public String convertFromString(String str) {
        return str;
    }
}
