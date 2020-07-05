package com.happy3w.etc.copydb.model;

import com.happy3w.etl.copydb.model.DataSetDefine;

public class ApiDemoTest {
    public void should_copy_by_dataset() {
        /**
         * from:
         *      datasource:
         *          table:
         *          identifyField:
         *          start:
         *          end:
         * to:
         *      datasource:
         *          table:
         *          excludeFields:
         */
        DataSetDefine dataSetDefine = DataSetDefine.builder()
                .table("abc")
                .sql("select * from ")
                .build();
    }
}
