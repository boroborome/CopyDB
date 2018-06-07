package com.happy3w.etl.copydb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataSetValue {
    private String table;
    private List<ColumnDefine> columns;
    private List<List<Object>> values;
}
