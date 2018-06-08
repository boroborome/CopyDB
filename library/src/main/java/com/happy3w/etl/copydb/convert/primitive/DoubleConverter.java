package com.happy3w.etl.copydb.convert.primitive;

import org.springframework.util.StringUtils;

public class DoubleConverter implements IPrimitiveConverter<Double> {
    @Override
    public Class<Double> dataType() {
        return Double.class;
    }
    @Override
    public Class primitiveType() {
        return Double.TYPE;
    }

    @Override
    public String convertToString(Double value) {
        return value == null ? null : String.valueOf(value);
    }

    @Override
    public Double convertFromString(String str) {
        return StringUtils.isEmpty(str) ? null : Double.parseDouble(str);
    }
}
