package com.happy3w.etc.copydb.model;

import com.happy3w.etl.copydb.model.ColumnDefine;
import com.happy3w.etl.copydb.model.DataSetValue;
import com.happy3w.etl.copydb.model.FileDataTermination;
import com.happy3w.etl.copydb.utils.MapBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class FileDataTerminationTest {
    @Test
    public void should_convert_string_to_json_success() {
        String str = "[{\"table\":\"tableName\",\"columns\":[{\"name\":\"name\",\"dataType\":\"nvarchar\",\"size\":0,\"autoIncrease\":false,\"nullable\":false},{\"name\":\"age\",\"dataType\":\"int\",\"size\":0,\"autoIncrease\":false,\"nullable\":false}],\"values\":[{\"name\":\"Jim\",\"age\":6},{\"name\":\"Tom\",\"age\":4}]}]";

        FileDataTermination fileDataTermination = FileDataTermination.fromJson(str, "fileName.json");
        Assert.assertNotNull(fileDataTermination);
        Assert.assertEquals("fileName.json", fileDataTermination.getFileName());
        Assert.assertEquals(1, fileDataTermination.getDataSetValues().size());

        DataSetValue dataSetValue = fileDataTermination.getDataSetValues().get(0);
        Assert.assertEquals(2, dataSetValue.getColumns().size());
        Assert.assertEquals(2, dataSetValue.getValues().size());
    }

    @Test
    public void should_convert_json_to_string_success() {
        FileDataTermination fileDataTermination = new FileDataTermination();
        fileDataTermination.setFileName("test.json");
        fileDataTermination.setDataSetValues(Arrays.asList(
                DataSetValue.builder()
                        .table("tableName")
                        .columns(Arrays.asList(
                                ColumnDefine.builder()
                                        .name("name")
                                        .dataType("nvarchar")
                                        .build(),
                                ColumnDefine.builder()
                                        .name("age")
                                        .dataType("int")
                                        .build()
                        ))
                        .values(Arrays.asList(
                                MapBuilder.<String, Object>of("name", "Jim")
                                    .and("age", 6)
                                    .build(),
                                MapBuilder.<String, Object>of("name", "Tom")
                                        .and("age", 4)
                                        .build()
                        ))
                        .build()
        ));

        String str = fileDataTermination.getJson();
        Assert.assertEquals("[{\"table\":\"tableName\",\"columns\":[{\"name\":\"name\",\"dataType\":\"nvarchar\",\"size\":0,\"autoIncrease\":false,\"nullable\":false},{\"name\":\"age\",\"dataType\":\"int\",\"size\":0,\"autoIncrease\":false,\"nullable\":false}],\"values\":[{\"name\":\"Jim\",\"age\":6},{\"name\":\"Tom\",\"age\":4}]}]",
                str);
    }
}
