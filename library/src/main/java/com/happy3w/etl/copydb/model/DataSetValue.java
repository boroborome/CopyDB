package com.happy3w.etl.copydb.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSetValue {
    private String table;
    private List<ColumnDefine> columns;
    private List<List<Object>> values;
}
