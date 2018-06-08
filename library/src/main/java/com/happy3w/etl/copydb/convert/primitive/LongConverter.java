package com.happy3w.etl.copydb.convert.primitive;

import org.springframework.util.StringUtils;

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
