package com.happy3w.etc.copydb.service;

import com.happy3w.etl.copydb.model.ColumnDefine;
import com.happy3w.etl.copydb.model.DataSetDefine;
import com.happy3w.etl.copydb.model.DataSetValue;
import com.happy3w.etl.copydb.model.FileDataTermination;
import com.happy3w.etl.copydb.service.TransferService;
import com.happy3w.etl.copydb.utils.MapBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@SpringBootTest(classes = TransferService.class)
public class TransferServiceTest {
    @Autowired
    private TransferService transferService;

    @Test
    public void should_transfer_from_file_to_file_success() {
        Assert.assertNotNull(transferService);

        FileDataTermination fileDataTerminationWithData = createTestFileDataTermination();
        FileDataTermination fileDataTermination = new FileDataTermination();
        transferService.transfer(DataSetDefine.builder()
                        .table("tableName")
                        .build(),
                fileDataTerminationWithData, fileDataTermination, null);

        Assert.assertEquals(1, fileDataTermination.getDataSetValues().size());
        DataSetValue dataSetValue = fileDataTermination.getDataSetValues().get(0);

        Assert.assertEquals(2, dataSetValue.getValues().size());
        Assert.assertEquals(2, dataSetValue.getColumns().size());
    }

    public FileDataTermination createTestFileDataTermination() {
        FileDataTermination fileDataTermination = new FileDataTermination();
        fileDataTermination.setFileName("test.json");
        fileDataTermination.setDataSetValues(Arrays.asList(
                DataSetValue.builder()
                        .table("tableName")
                        .columns(Arrays.asList(
                                ColumnDefine.builder()
                                        .name("name")
                                        .dataType("String")
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
        return fileDataTermination;
    }
}
