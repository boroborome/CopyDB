package com.happy3w.etl.copydb.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSetDefine {
    public static final String DataSetPrefix = "dataset.";
    private String table;
    private String sql;

    public static String dataSetDefineName(String dataSetName) {
        return DataSetPrefix + dataSetName;
    }
}
