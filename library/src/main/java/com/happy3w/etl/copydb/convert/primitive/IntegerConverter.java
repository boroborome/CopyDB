package com.happy3w.etl.copydb.convert.primitive;

import org.springframework.util.StringUtils;

public class IntegerConverter implements IPrimitiveConverter<Integer> {
    @Override
    public Class<Integer> dataType() {
        return Integer.class;
    }
    @Override
    public Class primitiveType() {
        return Integer.TYPE;
    }
    @Override
    public String convertToString(Integer value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Integer convertFromString(String str) {
        return StringUtils.isEmpty(str) ? null : Integer.parseInt(PrimitiveUtil.dropPointValue(str));
    }
}
