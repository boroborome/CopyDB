package com.happy3w.etc.copydb.convert;

import com.happy3w.etl.copydb.convert.ConvertFactoryProcessor;
import com.happy3w.etl.copydb.convert.primitive.PrimitiveConverter;
import org.junit.Assert;
import org.junit.Test;

public class PrimitiveUtilTest {
    @Test
    public void should_convert_string_to_int_success() {
        new ConvertFactoryProcessor().postProcessBeanFactory(null);
        String str = "10";
        int i = PrimitiveConverter.getInstance().convert(str, Integer.class);
        Assert.assertEquals(10, i);
    }
}
